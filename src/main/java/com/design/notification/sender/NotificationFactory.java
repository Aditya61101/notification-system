package com.design.notification.sender;

import com.design.notification.models.Channel;
import com.design.notification.sender.strategies.EmailStrategy;
import com.design.notification.sender.strategies.PushStrategy;
import com.design.notification.sender.strategies.SMSStrategy;

public class NotificationFactory {
    public static NotificationStrategy getStrategy(Channel channel) {
        return switch (channel) {
            case EMAIL -> new EmailStrategy();
            case SMS -> new SMSStrategy();
            case PUSH -> new PushStrategy();
        };
    }
}
