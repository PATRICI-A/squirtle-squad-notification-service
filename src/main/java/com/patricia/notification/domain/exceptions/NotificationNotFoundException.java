package com.patricia.notification.domain.exceptions;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(String notificationId) {
        super("No se encontró la notificación con id " + notificationId);
    }
}