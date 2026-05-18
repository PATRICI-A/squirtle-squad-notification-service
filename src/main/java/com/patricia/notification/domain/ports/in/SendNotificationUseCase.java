package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationType;

import java.util.UUID;

/**
 * Input port for creating and delivering a notification to a specific user.
 *
 * <p>Implementations must check user preferences before persisting and
 * attempt delivery through all registered {@code NotificationDeliveryPort}s
 * that match the resolved channel.</p>
 */
public interface SendNotificationUseCase {

    /**
     * Creates, persists and delivers a notification.
     *
     * @param userId      ID of the recipient user
     * @param type        category of the notification
     * @param title       short summary shown in notification lists (max 80 chars)
     * @param body        full notification message (max 200 chars)
     * @param referenceId optional ID of the entity that triggered this notification
     * @return the persisted {@link Notification}
     * @throws com.patricia.notification.domain.exceptions.NotificationTypeDisabledException
     *         if the user has disabled this notification type
     */
    Notification execute(UUID userId, NotificationType type,
                         String title, String body, UUID referenceId);
}
