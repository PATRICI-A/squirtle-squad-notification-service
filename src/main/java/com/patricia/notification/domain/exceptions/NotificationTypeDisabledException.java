package com.patricia.notification.domain.exceptions;

import com.patricia.notification.domain.model.enums.NotificationType;

import java.util.UUID;

public class NotificationTypeDisabledException extends RuntimeException {

    public NotificationTypeDisabledException(UUID userId, NotificationType type) {
        super("El usuario " + userId + " tiene desactivadas las notificaciones de tipo " + type.name());
    }
}
