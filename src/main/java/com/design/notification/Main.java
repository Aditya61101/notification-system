package com.design.notification;

import com.design.notification.models.Channel;
import com.design.notification.models.Notification;
import com.design.notification.sender.NotificationFactory;
import com.design.notification.sender.NotificationStrategy;

public class Main {
    public static void main(String[] args) {
        System.out.println("Notification system started");
        Notification n = new Notification();
        n.id = "notify1";
        n.userId = "user1";
        n.message = "Order placed!";
        n.channel = Channel.SMS;

        NotificationStrategy strategy = NotificationFactory.getStrategy(n.channel);
        strategy.send(n);
    }
}
