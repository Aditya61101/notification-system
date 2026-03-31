package com.design.notification.pipeline.handlers;

import com.design.notification.models.Notification;
import com.design.notification.pipeline.AbstractHandler;
import com.design.notification.sender.NotificationFactory;
import com.design.notification.sender.NotificationStrategy;

public class SendNotificationHandler extends AbstractHandler {

    @Override
    public void handle(Notification n) {
        NotificationStrategy strategy = NotificationFactory.getStrategy(n.channel);
        strategy.send(n);
    }
}
