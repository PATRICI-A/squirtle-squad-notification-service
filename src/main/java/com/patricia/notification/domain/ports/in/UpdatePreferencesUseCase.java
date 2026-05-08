package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationType;

public interface UpdatePreferencesUseCase {
    NotificationPreferences execute(String userId,
                                    NotificationType type, boolean enabled);
}