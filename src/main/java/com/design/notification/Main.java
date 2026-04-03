package com.design.notification;
// enums
import com.design.notification.enums.NotificationType;
import com.design.notification.enums.Channel;
import com.design.notification.enums.RateLimiterEnum;
// models
import com.design.notification.models.Notification;
import com.design.notification.models.UserPreference;
// pipeline
import com.design.notification.pipeline.NotificationHandler;
import com.design.notification.pipeline.handlers.PreferenceHandler;
import com.design.notification.pipeline.handlers.RateLimiterHandler;
import com.design.notification.pipeline.handlers.SendNotificationHandler;
import com.design.notification.pipeline.handlers.ValidationHandler;
// queues
import com.design.notification.queue.DeadLetterQueue;
import com.design.notification.queue.NotificationQueue;
// rate limiters
import com.design.notification.ratelimiters.RateLimiterConfig;
import com.design.notification.ratelimiters.configs.TokenBucketConfig;
// services
import com.design.notification.services.PreferenceService;
import com.design.notification.services.RateLimiterService;
// workers
import com.design.notification.workers.DeadNotificationWorker;
import com.design.notification.workers.NotificationWorker;

import java.util.Map;
import java.util.Set;

// Main wires everything.
public class Main {
    public static void main(String[] args) {
        System.out.println("Notification system started");

        NotificationQueue queue = new NotificationQueue();
        DeadLetterQueue deadLetterQueue = new DeadLetterQueue();

        RateLimiterConfig config = new TokenBucketConfig(5,5);
        RateLimiterService rateLimiterService = new RateLimiterService(RateLimiterEnum.TOKEN_BUCKET, config);

        PreferenceService preferenceService = new PreferenceService();
        for(int i=0;i<=50;i++) {
            UserPreference pref = new UserPreference();
            pref.userId = "user" + i;
            pref.allowedChannels = Map.of(NotificationType.ORDER_PLACED, Set.of(Channel.EMAIL));
            preferenceService.addPreference(pref);
        }

        NotificationHandler pipeline = new ValidationHandler().setNext(
                new PreferenceHandler(preferenceService).setNext(
                        new RateLimiterHandler(rateLimiterService).setNext(
                                new SendNotificationHandler()
                        )
                )
        );

        Thread workerThread = new Thread(new NotificationWorker(queue, deadLetterQueue, pipeline));
        Thread deadQueueThread = new Thread(new DeadNotificationWorker(deadLetterQueue));

        workerThread.start();
        deadQueueThread.start();

        // Simulating incoming notifications
        for(int i=0;i<=50;i++) {
            Notification n = new Notification();
            n.id = "notify" + i;
            n.userId = "user"+ i;
            n.message = "Order placed!";
            n.channel = Channel.EMAIL;
            n.type = NotificationType.ORDER_PLACED;

            queue.add(n);
            System.out.println("Notification added to queue for " + n.userId);
        }
    }
}
