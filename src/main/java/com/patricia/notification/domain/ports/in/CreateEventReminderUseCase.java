package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.EventReminder;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CreateEventReminderUseCase {
    EventReminder execute(UUID userId, UUID eventId, LocalDateTime eventDate);
}
