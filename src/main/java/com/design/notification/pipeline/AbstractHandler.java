package com.design.notification.pipeline;

import com.design.notification.models.Notification;

public abstract class AbstractHandler implements NotificationHandler {
    protected NotificationHandler next;

    public AbstractHandler setNext(NotificationHandler next) {
        this.next = next;
        return this;
    }

    protected void callNext(Notification n) {
        if(next!=null) {
            next.handle(n);
        }
    }

}
