package com.patricia.notification.domain.exceptions;

import java.util.UUID;

public class NotificationDailyLimitException extends RuntimeException {

    public NotificationDailyLimitException(UUID userId) {
        super("El usuario " + userId + " ha alcanzado el límite diario de notificaciones push");
    }
}