package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.EventReminder;
import java.util.List;
import java.util.Optional;

public interface EventReminderRepository {
    EventReminder save(EventReminder reminder);
    Optional<EventReminder> findByUserIdAndEventId(String userId, String eventId);
    List<EventReminder> findPendingReminders();
}