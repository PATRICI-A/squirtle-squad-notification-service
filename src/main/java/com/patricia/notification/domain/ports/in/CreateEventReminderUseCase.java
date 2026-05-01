package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.EventReminder;

import java.time.LocalDateTime;

public interface CreateEventReminderUseCase {
    EventReminder execute(String userId, String eventId, LocalDateTime eventDate);
}
