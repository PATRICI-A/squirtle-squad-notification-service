package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationType;

import java.util.UUID;

public interface SendNotificationUseCase {
    Notification execute(UUID userId, NotificationType type,
                         String title, String body, UUID referenceId);
}