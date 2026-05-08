package com.patricia.notification.infrastructure.adapters.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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