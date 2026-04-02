package com.design.notification.services;

import com.design.notification.enums.Channel;
import com.design.notification.enums.NotificationType;
import com.design.notification.models.Notification;
import com.design.notification.models.UserPreference;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PreferenceService {
    private final Map<String, UserPreference> store = new HashMap<>();

    public void addPreference(UserPreference pref) {
        store.put(pref.userId, pref);
    }

    public boolean isAllowed(Notification n) {
        UserPreference pref = store.get(n.userId);
        if(pref==null) return true;

        Set<Channel>allowed = pref.allowedChannels.get(n.type);
        return allowed!=null && allowed.contains(n.channel);
    }
}
