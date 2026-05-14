package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.EventReminder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventReminderRepository {
    EventReminder save(EventReminder reminder);
    Optional<EventReminder> findByUserIdAndEventId(UUID userId, UUID eventId);
    List<EventReminder> findPendingReminders();
}