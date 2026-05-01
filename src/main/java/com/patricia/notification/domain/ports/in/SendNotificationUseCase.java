package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationType;

public interface SendNotificationUseCase {
    Notification execute(String userId, NotificationType type,
                         String title, String body, String referenceId);
}