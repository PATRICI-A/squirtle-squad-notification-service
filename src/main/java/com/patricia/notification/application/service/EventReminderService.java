package com.patricia.notification.application.service;

import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.ports.out.EventReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventReminderService {

    private final EventReminderRepository eventReminderRepository;
    private final SendNotificationUseCase sendNotificationUseCase;

    @Scheduled(fixedDelay = 600000)
    public void processReminders() {
        List<EventReminder> pending = eventReminderRepository.findPendingReminders();

        for (EventReminder reminder : pending) {
            if (reminder.needs24hReminder()) {
                sendNotificationUseCase.execute(
                        reminder.getUserId(),
                        NotificationType.EVENT_REMINDER,
                        NotificationChannel.IN_APP,
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
                        NotificationChannel.IN_APP,
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