package com.patricia.notification.domain.exceptions;

public class FcmTokenNotFoundException extends RuntimeException {

    public FcmTokenNotFoundException(String userId) {
        super("No se encontró un token FCM registrado para el usuario " + userId);
    }
}