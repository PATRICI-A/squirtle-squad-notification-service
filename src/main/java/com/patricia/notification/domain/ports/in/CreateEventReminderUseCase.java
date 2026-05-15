package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.EventReminder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Input port for registering a new event reminder.
 *
 * <p>Once created, the reminder is picked up by the scheduler, which fires
 * notifications 24 hours and 1 hour before the event date.</p>
 */
public interface CreateEventReminderUseCase {

    /**
     * Registers an event reminder for the given user and event.
     *
     * @param userId    ID of the user who will receive the reminders
     * @param eventId   ID of the event to be reminded about
     * @param eventDate scheduled date and time of the event (must be in the future)
     * @return the persisted {@link EventReminder}
     */
    EventReminder execute(UUID userId, UUID eventId, LocalDateTime eventDate);
}
