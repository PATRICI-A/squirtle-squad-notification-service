package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationType;

import java.util.UUID;

/**
 * Input port for enabling or disabling a specific notification type for a user.
 */
public interface UpdatePreferencesUseCase {

    /**
     * Updates a single notification type preference for the specified user.
     *
     * @param userId  ID of the user whose preference to update
     * @param type    the notification type to configure
     * @param enabled {@code true} to enable, {@code false} to disable
     * @return the updated {@link NotificationPreferences}
     */
    NotificationPreferences execute(UUID userId, NotificationType type, boolean enabled);
}
