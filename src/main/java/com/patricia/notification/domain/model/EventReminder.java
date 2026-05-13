package com.patricia.notification.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Builder
public class EventReminder {

    private static final long WINDOW_MINUTES = 30;

    private String id;
    private UUID userId;
    private UUID eventId;
    private LocalDateTime eventDate;
    private boolean reminded24h;
    private boolean reminded1h;

    public boolean needs24hReminder() {
        if (this.reminded24h) return false;
        long minutesUntilEvent = ChronoUnit.MINUTES.between(
                LocalDateTime.now(), this.eventDate);
        return minutesUntilEvent >= (24 * 60 - WINDOW_MINUTES)
                && minutesUntilEvent <= (24 * 60 + WINDOW_MINUTES);
    }

    public boolean needs1hReminder() {
        if (this.reminded1h) return false;
        long minutesUntilEvent = ChronoUnit.MINUTES.between(
                LocalDateTime.now(), this.eventDate);
        return minutesUntilEvent >= (60 - WINDOW_MINUTES)
                && minutesUntilEvent <= (60 + WINDOW_MINUTES);
    }

    public void markReminded24h() {
        this.reminded24h = true;
    }

    public void markReminded1h() {
        this.reminded1h = true;
    }
}