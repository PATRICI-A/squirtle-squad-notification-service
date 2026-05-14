package com.patricia.notification.domain.exceptions;

import java.util.UUID;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(UUID notificationId) {
        super("No se encontró la notificación con id " + notificationId);
    }
}