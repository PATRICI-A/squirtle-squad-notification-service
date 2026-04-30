package com.patricia.notification.domain.exceptions;

public class InvalidNotificationException extends RuntimeException {

    public InvalidNotificationException(String reason) {
        super("Notificación inválida: " + reason);
    }
}