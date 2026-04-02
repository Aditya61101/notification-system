package com.design.notification.pipeline.handlers;

import com.design.notification.models.Notification;
import com.design.notification.pipeline.AbstractHandler;
import com.design.notification.services.PreferenceService;

public class PreferenceHandler extends AbstractHandler {
    private final PreferenceService preferenceService;

    public PreferenceHandler(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @Override
    public void handle(Notification n) {
        if(!preferenceService.isAllowed(n)) {
            System.out.println("Preference blocked for user " + n.userId);
            return;
        }
        System.out.println("Preference check passed!");
        // for now, we assume allowed
        callNext(n);
    }
}
