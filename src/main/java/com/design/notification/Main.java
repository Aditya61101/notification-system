package com.design.notification;

import com.design.notification.models.Channel;
import com.design.notification.models.Notification;
import com.design.notification.queue.DeadLetterQueue;
import com.design.notification.queue.NotificationQueue;
import com.design.notification.workers.DeadNotificationWorker;
import com.design.notification.workers.NotificationWorker;

public class Main {
    public static void main(String[] args) {
        System.out.println("Notification system started");

        NotificationQueue queue = new NotificationQueue();
        DeadLetterQueue deadLetterQueue = new DeadLetterQueue();

        Thread workerThread = new Thread(new NotificationWorker(queue, deadLetterQueue));
        Thread deadQueueThread = new Thread(new DeadNotificationWorker(deadLetterQueue));

        workerThread.start();
        deadQueueThread.start();

        // Simulating incoming notifications
        for(int i=0;i<=50;i++) {
            Notification n = new Notification();
            n.id = "notify" + i;
            n.userId = "user"+ i;
            n.message = "Order placed!";
            n.channel = Channel.EMAIL;
            queue.add(n);
            System.out.println("Notification added to queue for " + n.userId);
        }
    }
}
