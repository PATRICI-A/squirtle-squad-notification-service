package com.patricia.notification.domain.exceptions;

import com.patricia.notification.domain.model.enums.NotificationType;

import java.util.UUID;

/**
 * Thrown when a notification of a specific type cannot be delivered because the
 * recipient has disabled that notification category in their preferences.
 *
 * <p>Maps to HTTP 409 Conflict via the global exception handler.</p>
 */
public class NotificationTypeDisabledException extends RuntimeException {

    /**
     * @param userId the ID of the user who has disabled the notification type
     * @param type   the notification type that is currently disabled
     */
    public NotificationTypeDisabledException(UUID userId, NotificationType type) {
        super("El usuario " + userId + " tiene desactivadas las notificaciones de tipo " + type.name());
    }
}
