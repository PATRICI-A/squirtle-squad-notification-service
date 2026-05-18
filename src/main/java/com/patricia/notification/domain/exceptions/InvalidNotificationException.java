package com.patricia.notification.domain.exceptions;

/**
 * Thrown when a notification cannot be created due to missing or invalid field values.
 *
 * <p>Maps to HTTP 400 Bad Request via the global exception handler.</p>
 */
public class InvalidNotificationException extends RuntimeException {

    /**
     * @param reason human-readable description of the validation failure
     */
    public InvalidNotificationException(String reason) {
        super("Notificación inválida: " + reason);
    }
}
