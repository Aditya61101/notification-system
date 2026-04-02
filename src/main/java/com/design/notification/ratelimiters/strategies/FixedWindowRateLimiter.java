package com.design.notification.ratelimiters.strategies;

import com.design.notification.ratelimiters.RateLimiterStrategy;

public class FixedWindowRateLimiter implements RateLimiterStrategy {
    private final int limit;
    private final long windowSizeMillis;

    private int count;
    private long windowStart;

    public FixedWindowRateLimiter(int limit, long windowSizeMillis) {
        this.limit = limit;
        this.windowSizeMillis = windowSizeMillis;
        this.windowStart = System.currentTimeMillis();
        this.count = 0;
    }

    @Override
    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();

        if(now-windowStart >= windowSizeMillis) {
            windowStart = now;
            count = 0;
        }
        if(count < limit) {
            count++;
            return true;
        }
        return false;
    }
}
