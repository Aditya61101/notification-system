package com.design.notification.workers;

import com.design.notification.models.Notification;
import com.design.notification.queue.DeadLetterQueue;
// import com.design.notification.sender.NotificationFactory;
// import com.design.notification.sender.NotificationStrategy;

public class DeadNotificationWorker implements Runnable {
    private final DeadLetterQueue dLQ;

    public DeadNotificationWorker(DeadLetterQueue dq) {
        this.dLQ = dq;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Notification n = dLQ.take();
//                NotificationStrategy strategy = NotificationFactory.getStrategy(n.channel);
//                strategy.send(n);
                System.out.println("DLQ processing " + n.userId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
