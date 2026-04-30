package com.patricia.notification.domain.exceptions;

import com.patricia.notification.domain.model.enums.NotificationType;

public class NotificationTypeDisabledException extends RuntimeException {

    public NotificationTypeDisabledException(String userId, NotificationType type) {
        super("El usuario " + userId + " tiene desactivadas las notificaciones de tipo " + type.name());
    }
}
