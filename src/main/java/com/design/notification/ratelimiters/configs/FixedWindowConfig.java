package com.design.notification.ratelimiters.configs;

import com.design.notification.ratelimiters.RateLimiterConfig;

public class FixedWindowConfig implements RateLimiterConfig {
    public final int limit;
    public final long windowSizeMillis;

    FixedWindowConfig(int limit, long wdwSizeMillis) {
        this.limit = limit;
        this.windowSizeMillis = wdwSizeMillis;
    }
}
