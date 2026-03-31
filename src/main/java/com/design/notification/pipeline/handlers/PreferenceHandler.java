package com.design.notification.pipeline.handlers;

import com.design.notification.models.Notification;
import com.design.notification.pipeline.AbstractHandler;

public class PreferenceHandler extends AbstractHandler {

    @Override
    public void handle(Notification n) {
        System.out.println("Preference check passed!");
        // for now, we assume allowed
        callNext(n);
    }
}
