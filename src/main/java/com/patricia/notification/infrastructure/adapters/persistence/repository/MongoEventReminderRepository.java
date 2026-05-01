package com.patricia.notification.infrastructure.adapters.persistence.repository;

import com.patricia.notification.infrastructure.adapters.persistence.entity.EventReminderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MongoEventReminderRepository
        extends MongoRepository<EventReminderDocument, String> {

    Optional<EventReminderDocument> findByUserIdAndEventId(
            String userId, String eventId);

    @Query("{'$or': [{'reminded24h': false}, {'reminded1h': false}]}")
    List<EventReminderDocument> findPendingReminders();
}