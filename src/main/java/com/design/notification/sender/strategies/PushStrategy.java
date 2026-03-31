package com.design.notification.sender.strategies;

import com.design.notification.models.Notification;
import com.design.notification.sender.NotificationStrategy;

public class PushStrategy implements NotificationStrategy {
    @Override
    public void send(Notification n) {
         System.out.println("Sending push notification to " + n.userId + ": " + n.message);
    }
}
