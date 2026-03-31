package com.design.notification.pipeline.handlers;

import com.design.notification.models.Notification;
import com.design.notification.pipeline.AbstractHandler;

public class ValidationHandler extends AbstractHandler {
    @Override
    public void handle(Notification n) {
        if(n.userId==null || n.message==null) {
            System.out.println("Invalid notification");
            return;
        }
        callNext(n);
    }
}
