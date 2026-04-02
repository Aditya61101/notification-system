package com.design.notification.pipeline.handlers;

import com.design.notification.models.Notification;
import com.design.notification.pipeline.AbstractHandler;
import com.design.notification.ratelimiters.RateLimiterStrategy;

public class RateLimiterHandler extends AbstractHandler {
//    private final TokenBucketRateLimiter tokenBucket;
    private final RateLimiterStrategy rateLimiterStrategy;

    public RateLimiterHandler(RateLimiterStrategy rateLimiterStrategy) {
//        this.tokenBucket = tokenBucket;
        this.rateLimiterStrategy = rateLimiterStrategy;
    }

    @Override
    public void handle(Notification n) {
        if(!rateLimiterStrategy.allowRequest()) {
            System.out.println("Rate limit exceeded for " + n.userId);
            return;
        }
        callNext(n);
    }
}
