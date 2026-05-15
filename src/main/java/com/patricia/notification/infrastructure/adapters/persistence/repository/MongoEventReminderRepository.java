package com.patricia.notification.infrastructure.adapters.persistence.repository;

import com.patricia.notification.infrastructure.adapters.persistence.entity.EventReminderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for {@link EventReminderDocument}.
 */
public interface MongoEventReminderRepository
        extends MongoRepository<EventReminderDocument, String> {

    /**
     * Finds a reminder by user and event, used to detect duplicate registrations.
     *
     * @param userId  the user ID (string form)
     * @param eventId the event ID (string form)
     * @return an {@link Optional} containing the reminder, or empty if not found
     */
    Optional<EventReminderDocument> findByUserIdAndEventId(
            String userId, String eventId);

    /**
     * Returns all reminders that still need at least one notification sent.
     * Called by the scheduler every 10 minutes.
     *
     * <p>A reminder is considered pending if {@code reminded24h} or {@code reminded1h}
     * is {@code false}.</p>
     *
     * @return list of pending reminder documents
     */
    @Query("{'$or': [{'reminded24h': false}, {'reminded1h': false}]}")
    List<EventReminderDocument> findPendingReminders();
}
