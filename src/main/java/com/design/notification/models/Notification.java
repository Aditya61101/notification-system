package com.design.notification.models;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Notification implements Delayed {
    public String id;
    public String userId;
    public String message;
    public Channel channel;
    public int retryCount = 0;
    public static final int MAX_RETRIES = 3;

    public long nextRetryTime = 0;

    @Override
    public long getDelay(TimeUnit unit) {
        long delay = nextRetryTime - System.currentTimeMillis();
        return unit.convert(delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(nextRetryTime, ((Notification) o).nextRetryTime);
    }

}
