package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.NotificationPreferences;

public interface GetPreferencesUseCase {
    NotificationPreferences execute(String userId);
}