package com.patricia.notification.application.service;

import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.ports.out.EventReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Scheduled service that polls pending event reminders and fires notifications
 * at the appropriate time windows.
 *
 * <p>Runs every 10 minutes ({@code fixedDelay = 600000 ms}). For each pending reminder,
 * it independently checks whether the 24-hour or 1-hour notification should fire,
 * sends the notification via {@link SendNotificationUseCase}, and persists the updated
 * reminder flags to prevent duplicate sends.</p>
 *
 * <p>Both checks are independent per iteration: if an event is within the 1-hour window
 * and the 24-hour notification was already sent, only the 1-hour notification fires.</p>
 */
@Service
@RequiredArgsConstructor
public class EventReminderService {

    private final EventReminderRepository eventReminderRepository;
    private final SendNotificationUseCase sendNotificationUseCase;

    /**
     * Processes all pending reminders. Invoked automatically every 10 minutes.
     */
    @Scheduled(fixedDelay = 600000)
    public void processReminders() {
        List<EventReminder> pending = eventReminderRepository.findPendingReminders();

        for (EventReminder reminder : pending) {
            if (reminder.needs24hReminder()) {
                sendNotificationUseCase.execute(
                        reminder.getUserId(),
                        NotificationType.EVENT_REMINDER,
                        "Recordatorio de evento",
                        "Tu evento comienza en 24 horas",
                        reminder.getEventId()
                );
                reminder.markReminded24h();
                eventReminderRepository.save(reminder);
            }

            if (reminder.needs1hReminder()) {
                sendNotificationUseCase.execute(
                        reminder.getUserId(),
                        NotificationType.EVENT_REMINDER,
                        "Recordatorio de evento",
                        "Tu evento comienza en 1 hora",
                        reminder.getEventId()
                );
                reminder.markReminded1h();
                eventReminderRepository.save(reminder);
            }
        }
    }
}
