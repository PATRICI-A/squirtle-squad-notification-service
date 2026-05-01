package com.patricia.notification.infrastructure.adapters.persistence.mapper;

import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.infrastructure.adapters.persistence.entity.EventReminderDocument;
import org.springframework.stereotype.Component;

@Component
public class EventReminderPersistenceMapper {

    public EventReminderDocument toDocument(EventReminder reminder) {
        return EventReminderDocument.builder()
                .id(reminder.getId())
                .userId(reminder.getUserId())
                .eventId(reminder.getEventId())
                .eventDate(reminder.getEventDate())
                .reminded24h(reminder.isReminded24h())
                .reminded1h(reminder.isReminded1h())
                .build();
    }

    public EventReminder toDomain(EventReminderDocument doc) {
        return EventReminder.builder()
                .id(doc.getId())
                .userId(doc.getUserId())
                .eventId(doc.getEventId())
                .eventDate(doc.getEventDate())
                .reminded24h(doc.isReminded24h())
                .reminded1h(doc.isReminded1h())
                .build();
    }
}