package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.NotificationPreferences;

import java.util.UUID;

public interface GetPreferencesUseCase {
    NotificationPreferences execute(UUID userId);
}