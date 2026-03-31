package com.design.notification.queue;

import com.design.notification.models.Notification;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NotificationQueue {
    private final BlockingQueue<Notification> queue = new LinkedBlockingQueue<>();

    public void add(Notification n) {
        // When using a capacity-restricted queue,
        // this method is generally preferable to add,
        // allowing for graceful handling via a boolean return value.
        queue.offer(n);
    }
    public Notification take() throws InterruptedException {
        return queue.take();
    }
}
