package com.patricia.notification.domain.exceptions;

public class NotificationDailyLimitException extends RuntimeException {

    public NotificationDailyLimitException(String userId) {
        super("El usuario " + userId + " ha alcanzado el límite diario de notificaciones push");
    }
}