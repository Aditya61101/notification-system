package com.design.notification.models;

import com.design.notification.enums.Channel;
import com.design.notification.enums.NotificationType;

import java.util.Map;
import java.util.Set;

public class UserPreference {
    public String userId;
    public Map<NotificationType, Set<Channel>> allowedChannels;
}
