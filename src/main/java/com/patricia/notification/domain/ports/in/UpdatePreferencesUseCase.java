package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationType;

import java.util.UUID;

public interface UpdatePreferencesUseCase {
    NotificationPreferences execute(UUID userId,
                                    NotificationType type, boolean enabled);
}