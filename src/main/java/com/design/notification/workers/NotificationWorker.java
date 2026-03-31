package com.design.notification.workers;

import com.design.notification.models.Notification;
import com.design.notification.queue.NotificationQueue;
import com.design.notification.sender.NotificationFactory;
import com.design.notification.sender.NotificationStrategy;

public class NotificationWorker implements Runnable {
    private final NotificationQueue queue;
    public NotificationWorker(NotificationQueue queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        // the condition uses 'true' and doesn't check size since the queue is not a fixed list, it's a continuous stream of work
        while(true) {
            try {
                Notification n = queue.take();
                NotificationStrategy strategy = NotificationFactory.getStrategy(n.channel);
                strategy.send(n);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
