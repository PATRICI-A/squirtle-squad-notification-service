package com.patricia.notification.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Tracks scheduled reminders for upcoming events.
 *
 * <p>The scheduler checks pending reminders every 10 minutes. Each reminder may fire
 * up to two notifications per event: one at 24 hours before and another at 1 hour
 * before. A ±{@value #WINDOW_MINUTES}-minute tolerance window is applied to each
 * check to account for scheduling jitter.</p>
 */
@Getter
@Builder
public class EventReminder {

    /** Tolerance window in minutes applied to each reminder time check. */
    private static final long WINDOW_MINUTES = 30;

    private String id;
    private UUID userId;
    private UUID eventId;
    private LocalDateTime eventDate;

    /** Whether the 24-hour-before notification has already been sent. */
    private boolean reminded24h;

    /** Whether the 1-hour-before notification has already been sent. */
    private boolean reminded1h;

    /**
     * Returns {@code true} if the 24-hour reminder should fire now.
     *
     * <p>Fires when the event is between 23h30m and 24h30m away and the reminder
     * has not been sent yet.</p>
     */
    public boolean needs24hReminder() {
        if (this.reminded24h) return false;
        long minutesUntilEvent = ChronoUnit.MINUTES.between(
                LocalDateTime.now(), this.eventDate);
        return minutesUntilEvent >= (24 * 60 - WINDOW_MINUTES)
                && minutesUntilEvent <= (24 * 60 + WINDOW_MINUTES);
    }

    /**
     * Returns {@code true} if the 1-hour reminder should fire now.
     *
     * <p>Fires when the event is between 30m and 1h30m away and the reminder
     * has not been sent yet.</p>
     */
    public boolean needs1hReminder() {
        if (this.reminded1h) return false;
        long minutesUntilEvent = ChronoUnit.MINUTES.between(
                LocalDateTime.now(), this.eventDate);
        return minutesUntilEvent >= (60 - WINDOW_MINUTES)
                && minutesUntilEvent <= (60 + WINDOW_MINUTES);
    }

    /**
     * Marks the 24-hour reminder as sent. Subsequent calls to
     * {@link #needs24hReminder()} will return {@code false}.
     */
    public void markReminded24h() {
        this.reminded24h = true;
    }

    /**
     * Marks the 1-hour reminder as sent. Subsequent calls to
     * {@link #needs1hReminder()} will return {@code false}.
     */
    public void markReminded1h() {
        this.reminded1h = true;
    }
}
