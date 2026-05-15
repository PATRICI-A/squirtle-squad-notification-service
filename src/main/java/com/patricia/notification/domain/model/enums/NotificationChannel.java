package com.patricia.notification.domain.model.enums;

/**
 * Delivery channels available for notifications.
 */
public enum NotificationChannel {

    /** Real-time delivery via WebSocket while the user is connected to the application. */
    IN_APP,

    /** Delivery via SMTP email, used for auth-related notifications such as OTP and password reset. */
    EMAIL
}
