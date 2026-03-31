package com.design.notification.sender;

import com.design.notification.models.Notification;

public interface NotificationStrategy {
    void send(Notification notification);
}
