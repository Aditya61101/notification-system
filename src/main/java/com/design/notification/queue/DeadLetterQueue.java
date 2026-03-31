package com.design.notification.queue;

import com.design.notification.models.Notification;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// A queue where notification which failed even after MAX_RETRIES are kept.
public class DeadLetterQueue {
    private final BlockingQueue<Notification> deadQueue = new LinkedBlockingQueue<>();

    public void add(Notification n) {
        deadQueue.offer(n);
    }

    public Notification take() throws InterruptedException {
        return deadQueue.take();
    }
 }
