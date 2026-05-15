package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.NotificationPreferences;
import java.util.Optional;
import java.util.UUID;

/**
 * Output port for persisting and retrieving user notification preferences.
 *
 * <p>Implemented by the MongoDB persistence adapter in the infrastructure layer.</p>
 */
public interface PreferencesRepository {

    /**
     * Persists new preferences or replaces existing ones for the user.
     *
     * @param preferences the preferences to save
     * @return the saved preferences
     */
    NotificationPreferences save(NotificationPreferences preferences);

    /**
     * Finds the notification preferences for the given user.
     *
     * @param userId the user ID
     * @return an {@link Optional} containing the preferences, or empty if no record exists
     */
    Optional<NotificationPreferences> findByUserId(UUID userId);
}
