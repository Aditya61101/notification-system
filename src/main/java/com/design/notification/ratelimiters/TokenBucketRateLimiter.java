package com.design.notification.ratelimiters;

public class TokenBucketRateLimiter {
    private final int capacity;
    private final int refillRate; // tokens per second
    private int tokens;
    private long lastRefillTime;
    // Time unit for the token refill (in milliseconds).
    private final long windowUnit = 1000;

    public TokenBucketRateLimiter(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean allowRequest() {
        refill();
        if(tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastRefillTime;
        // Calculate the number of tokens to add based on elapsed time since the last refill.
        int tokensToAdd = (int) ((elapsed/windowUnit)*refillRate);
        if(tokensToAdd > 0) {
            tokens = Math.min(capacity, tokens+tokensToAdd);
            lastRefillTime = now;
        }
    }
}
