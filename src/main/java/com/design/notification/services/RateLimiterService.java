package com.design.notification.services;

import com.design.notification.enums.RateLimiterEnum;
import com.design.notification.ratelimiters.RateLimiterConfig;
import com.design.notification.ratelimiters.RateLimiterFactory;
import com.design.notification.ratelimiters.RateLimiterStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterService {
    // used concurrentHashmap since for a user,
    // 2 threads can create a rate limiter instance causing race condition. Normal hashmap is not thread safe.
    private final Map<String, RateLimiterStrategy> userRateLimiters = new ConcurrentHashMap<>();
    private final RateLimiterEnum type;
    private final RateLimiterConfig config;

    public RateLimiterService(RateLimiterEnum type, RateLimiterConfig config) {
        this.type = type;
        this.config = config;
    }

    public RateLimiterStrategy getLimiter(String userId) {
        // conCurrentHashmap alone only guarantees each operation is thread safe.
        // computeIfAbsent "check+create+insert -> atomic". locks only necessary segment.
        // ensures only one creation.
        // other threads reuse result.
        return userRateLimiters.computeIfAbsent(userId, id -> RateLimiterFactory.getStrategy(type, config));
    }

}
