package com.patricia.notification.infrastructure.adapters.persistence.mapper;

import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.infrastructure.adapters.persistence.entity.EventReminderDocument;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EventReminderPersistenceMapper {

    public EventReminderDocument toDocument(EventReminder reminder) {
        return EventReminderDocument.builder()
                .id(reminder.getId() != null ? reminder.getId() : null)
                .userId(reminder.getUserId() != null ? reminder.getUserId().toString() : null)
                .eventId(reminder.getEventId() != null ? reminder.getEventId().toString() : null)
                .eventDate(reminder.getEventDate())
                .reminded24h(reminder.isReminded24h())
                .reminded1h(reminder.isReminded1h())
                .build();
    }

    public EventReminder toDomain(EventReminderDocument doc) {
        return EventReminder.builder()
                .id(doc.getId())
                .userId(doc.getUserId() != null ? UUID.fromString(doc.getUserId()) : null)
                .eventId(doc.getEventId() != null ? UUID.fromString(doc.getEventId()) : null)
                .eventDate(doc.getEventDate())
                .reminded24h(doc.isReminded24h())
                .reminded1h(doc.isReminded1h())
                .build();
    }
}