package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.EventReminder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Output port for persisting and querying event reminders.
 *
 * <p>Implemented by the MongoDB persistence adapter in the infrastructure layer.</p>
 */
public interface EventReminderRepository {

    /**
     * Persists a new event reminder or updates an existing one.
     *
     * @param reminder the reminder to save
     * @return the saved reminder
     */
    EventReminder save(EventReminder reminder);

    /**
     * Finds a reminder by user and event, used to avoid duplicate registrations.
     *
     * @param userId  the user ID
     * @param eventId the event ID
     * @return an {@link Optional} containing the reminder, or empty if not found
     */
    Optional<EventReminder> findByUserIdAndEventId(UUID userId, UUID eventId);

    /**
     * Returns all reminders that have not yet fired both notifications.
     * Called by the scheduler every 10 minutes.
     *
     * @return list of reminders still pending at least one notification
     */
    List<EventReminder> findPendingReminders();
}
