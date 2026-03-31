package com.design.notification.sender.strategies;

import com.design.notification.models.Notification;
import com.design.notification.sender.NotificationStrategy;

public class SMSStrategy implements NotificationStrategy {
    @Override
    public void send(Notification n) {
        System.out.println("Sending SMS to " + n.userId + ": "+n.message);
    }
}
