package com.design.notification.ratelimiters;

public interface RateLimiterStrategy {
    boolean allowRequest();
}
