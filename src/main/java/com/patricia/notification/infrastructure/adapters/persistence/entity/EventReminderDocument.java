package com.patricia.notification.infrastructure.adapters.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MongoDB document representing an event reminder.
 *
 * <p>The compound unique index on {@code (userId, eventId)} prevents duplicate
 * reminders for the same user-event pair. The {@code reminded24h} and {@code reminded1h}
 * flags are set to {@code true} after each notification is sent to prevent
 * duplicate firing across scheduler runs.</p>
 *
 * <p>Maps to the {@code event_reminders} collection.</p>
 */
@Getter
@Setter
@Builder
@Document(collection = "event_reminders")
@CompoundIndex(name = "userId_eventId", def = "{'userId': 1, 'eventId': 1}", unique = true)
public class EventReminderDocument {

    @Id
    private String id;
    private String userId;
    private String eventId;
    private LocalDateTime eventDate;
    private boolean reminded24h;
    private boolean reminded1h;
}
