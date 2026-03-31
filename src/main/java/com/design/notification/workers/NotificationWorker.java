package com.design.notification.workers;

import com.design.notification.models.Notification;
import com.design.notification.pipeline.handlers.RateLimiterHandler;
import com.design.notification.queue.DeadLetterQueue;
import com.design.notification.queue.NotificationQueue;
// for chain of responsibility pattern
import com.design.notification.pipeline.NotificationHandler;
import com.design.notification.pipeline.handlers.PreferenceHandler;
import com.design.notification.pipeline.handlers.SendNotificationHandler;
import com.design.notification.pipeline.handlers.ValidationHandler;

import com.design.notification.ratelimiters.TokenBucketRateLimiter;

public class NotificationWorker implements Runnable {
    private final NotificationQueue queue;
    private final DeadLetterQueue deadLetterQueue;
    private final NotificationHandler pipeline;

    public NotificationWorker(NotificationQueue queue, DeadLetterQueue deadLetterQueue) {
        this.queue = queue;
        this.deadLetterQueue = deadLetterQueue;

        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(5,5);
        this.pipeline = new ValidationHandler().setNext(
                new PreferenceHandler().setNext(
                        new RateLimiterHandler(limiter).setNext(
                                new SendNotificationHandler()
                        )
                )
        );
    }
    @Override
    public void run() {
        // the condition uses 'true' and doesn't check size since the queue is not a fixed list but a continuous stream of work.
        while(true) {
            try {
                Notification n = queue.take();
                try {
//                    NotificationStrategy strategy = NotificationFactory.getStrategy(n.channel);
//                    strategy.send(n);
                    pipeline.handle(n);
                } catch (Exception e) {
                    n.retryCount+=1;
                    System.out.println("FAILED for "+ n.userId + " retry count= " + n.retryCount);
                    if(n.retryCount <= Notification.MAX_RETRIES) {
                        // exponential backoff logic, will be taken out after the delay ends.
                        long delay = (long) Math.pow(2, n.retryCount) * 1000;
                        n.nextRetryTime = System.currentTimeMillis() + delay;
                        System.out.println("Retrying " + n.userId + " after " + delay + "ms.");
                        // addition of failed notification happens immediately
                        queue.add(n);
                    } else {
                        deadLetterQueue.add(n);
                        System.out.println("Notification for "+ n.userId + " moved to a dead queue.");
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
