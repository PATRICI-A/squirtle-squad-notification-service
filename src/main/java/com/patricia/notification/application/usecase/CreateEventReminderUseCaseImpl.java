package com.patricia.notification.application.usecase;

import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.domain.ports.in.CreateEventReminderUseCase;
import com.patricia.notification.domain.ports.out.EventReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateEventReminderUseCaseImpl implements CreateEventReminderUseCase {

    private final EventReminderRepository eventReminderRepository;

    @Override
    public EventReminder execute(String userId, String eventId, LocalDateTime eventDate) {
        EventReminder reminder = EventReminder.builder()
                .userId(userId)
                .eventId(eventId)
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(false)
                .build();

        return eventReminderRepository.save(reminder);
    }
}
