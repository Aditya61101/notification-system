package com.design.notification.sender.naive;

import com.design.notification.models.Notification;

// currently NotificationSender acts as God class as it knows about every channel, logic etc.
// also using strings like EMAIL, SMS is error-prone and fragile.
// violates Open Closed principle, for every new channel we need to write an if-else block, hence modifying the existing logic.
public class NotificationSender {
    public void send(Notification n) {
        if(n.channel.equals("EMAIL")) {
            sendEmail(n);
        } else if(n.channel.equals("SMS")) {
            sendSMS(n);
        }
    }

    public void sendEmail(Notification n) {
        System.out.println("Sending Email to " + n.userId + ": " + n.message);
    }

    public void sendSMS(Notification n) {
        System.out.println("Sending SMS to " + n.userId + ": " + n.message);
    }
}
