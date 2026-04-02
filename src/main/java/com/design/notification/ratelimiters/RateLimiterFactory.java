package com.design.notification.ratelimiters;

import com.design.notification.models.RateLimiterEnum;
import com.design.notification.ratelimiters.configs.FixedWindowConfig;
import com.design.notification.ratelimiters.configs.TokenBucketConfig;
import com.design.notification.ratelimiters.strategies.FixedWindowRateLimiter;
import com.design.notification.ratelimiters.strategies.TokenBucketRateLimiter;

public class RateLimiterFactory {
    public static RateLimiterStrategy getStrategy (RateLimiterEnum type, RateLimiterConfig config) {
        return switch(type) {
            case TOKEN_BUCKET -> {
                TokenBucketConfig c = (TokenBucketConfig) config;
                yield new TokenBucketRateLimiter(c.capacity, c.refillRate);
            }
            case FIXED_WINDOW -> {
                FixedWindowConfig c = (FixedWindowConfig) config;
                yield new FixedWindowRateLimiter(c.limit, c.windowSizeMillis);
            }
        };
    }

}
