package com.patricia.notification.domain.model;

import com.patricia.notification.domain.exceptions.NotificationDailyLimitException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class NotificationQuota {

    private static final int DAILY_LIMIT = 10;

    private String id;
    private String userId;
    private LocalDate date;
    private int pushCount;

    public void increment() {
        if (hasReachedLimit()) {
            throw new NotificationDailyLimitException(this.userId);
        }
        this.pushCount++;
    }

    public boolean hasReachedLimit() {
        return this.pushCount >= DAILY_LIMIT;
    }

    public int getRemaining() {
        return Math.max(0, DAILY_LIMIT - this.pushCount);
    }
}