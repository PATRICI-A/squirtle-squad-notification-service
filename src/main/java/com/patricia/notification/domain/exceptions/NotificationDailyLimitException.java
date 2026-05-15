package com.patricia.notification.domain.exceptions;

import java.util.UUID;

/**
 * Thrown when a user has reached the maximum number of push notifications
 * allowed within a single day.
 *
 * <p>Maps to HTTP 429 Too Many Requests via the global exception handler.</p>
 */
public class NotificationDailyLimitException extends RuntimeException {

    /**
     * @param userId the ID of the user who has reached the daily limit
     */
    public NotificationDailyLimitException(UUID userId) {
        super("El usuario " + userId + " ha alcanzado el límite diario de notificaciones push");
    }
}
