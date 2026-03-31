package com.design.notification.sender.strategies;

import com.design.notification.models.Notification;
import com.design.notification.sender.NotificationStrategy;

public class EmailStrategy implements NotificationStrategy {
    @Override
    public void send(Notification n) {
        System.out.println("Sending EMAIL to " + n.userId + ": " + n.message);
    }
}
