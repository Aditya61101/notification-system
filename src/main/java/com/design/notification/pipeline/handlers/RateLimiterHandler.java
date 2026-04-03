package com.design.notification.pipeline.handlers;

import com.design.notification.models.Notification;
import com.design.notification.pipeline.AbstractHandler;
import com.design.notification.ratelimiters.RateLimiterStrategy;
import com.design.notification.services.RateLimiterService;

public class RateLimiterHandler extends AbstractHandler {
    private final RateLimiterService rateLimiterService;

    public RateLimiterHandler(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    public void handle(Notification n) {
        RateLimiterStrategy limiter = rateLimiterService.getLimiter(n.userId);
        if(!limiter.allowRequest()) {
            System.out.println("Rate limit exceeded for " + n.userId);
            return;
        }
        callNext(n);
    }
}
