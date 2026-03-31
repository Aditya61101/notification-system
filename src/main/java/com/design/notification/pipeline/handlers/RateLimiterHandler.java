package com.design.notification.pipeline.handlers;

import com.design.notification.models.Notification;
import com.design.notification.pipeline.AbstractHandler;
import com.design.notification.ratelimiters.TokenBucketRateLimiter;

public class RateLimiterHandler extends AbstractHandler {
    private final TokenBucketRateLimiter tokenBucket;

    public RateLimiterHandler(TokenBucketRateLimiter tokenBucket) {
        this.tokenBucket = tokenBucket;
    }

    @Override
    public void handle(Notification n) {
        if(!tokenBucket.allowRequest()) {
            System.out.println("Rate limit exceeded for " + n.userId);
            return;
        }
        callNext(n);
    }
}
