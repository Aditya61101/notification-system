package com.design.notification;

import com.design.notification.models.Channel;
import com.design.notification.models.Notification;
import com.design.notification.queue.NotificationQueue;
import com.design.notification.workers.NotificationWorker;

public class Main {
    public static void main(String[] args) {
        System.out.println("Notification system started");
        NotificationQueue queue = new NotificationQueue();

        Thread workerThread = new Thread(new NotificationWorker(queue));
        workerThread.start();
        // Simulating incoming notifications
        for(int i=0;i<=5;i++) {
            Notification n = new Notification();
            n.id = "notify" + i;
            n.userId = "user"+ i;
            n.message = "Order placed!";
            n.channel = Channel.SMS;
            queue.add(n);
            System.out.println("Notification added to queue for " + n.userId);
        }
    }
}
