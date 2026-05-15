package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.NotificationPreferences;

import java.util.UUID;

/**
 * Input port for retrieving a user's notification preferences.
 *
 * <p>If no preferences record exists for the user, a default configuration is
 * returned with all types enabled except {@code NEARBY_PARCHE}.</p>
 */
public interface GetPreferencesUseCase {

    /**
     * Returns the notification preferences for the specified user.
     *
     * @param userId ID of the user
     * @return existing preferences, or default preferences if none have been saved
     */
    NotificationPreferences execute(UUID userId);
}
