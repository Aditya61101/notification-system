package com.design.notification.sender.strategies;

import com.design.notification.models.Notification;
import com.design.notification.sender.NotificationStrategy;

import java.util.Random;

public class EmailStrategy implements NotificationStrategy {
    private static final Random random = new Random();
    @Override
    public void send(Notification n) {
        // random failure
        if(random.nextBoolean()) {
            throw new RuntimeException("Email service failed");
        }
        System.out.println("Sending EMAIL to " + n.userId + ": " + n.message);
    }
}
