package com.design.notification.ratelimiters.configs;

import com.design.notification.ratelimiters.RateLimiterConfig;

public class TokenBucketConfig implements RateLimiterConfig {
    public final int capacity;
    public final int refillRate;

    public TokenBucketConfig(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
    }
}
