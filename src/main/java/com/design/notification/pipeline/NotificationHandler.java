package com.design.notification.pipeline;

import com.design.notification.models.Notification;

public interface NotificationHandler {
    void handle(Notification n);
}
