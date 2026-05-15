package com.patricia.notification.domain.exceptions;

import java.util.UUID;

/**
 * Thrown when a notification lookup by ID returns no result.
 *
 * <p>Maps to HTTP 404 Not Found via the global exception handler.</p>
 */
public class NotificationNotFoundException extends RuntimeException {

    /**
     * @param notificationId the ID that was not found in the repository
     */
    public NotificationNotFoundException(UUID notificationId) {
        super("No se encontró la notificación con id " + notificationId);
    }
}
