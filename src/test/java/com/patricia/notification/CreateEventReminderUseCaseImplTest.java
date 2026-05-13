package com.patricia.notification;

import com.patricia.notification.application.usecase.CreateEventReminderUseCaseImpl;
import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.domain.ports.out.EventReminderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateEventReminderUseCaseImplTest {

    private static final UUID USER_ID_1  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID USER_ID_3  = UUID.fromString("00000000-0000-0000-0000-000000000003");
    private static final UUID EVENT_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID EVENT_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000011");
    private static final UUID EVENT_ID_3 = UUID.fromString("00000000-0000-0000-0000-000000000012");

    @Mock
    private EventReminderRepository eventReminderRepository;

    @InjectMocks
    private CreateEventReminderUseCaseImpl useCase;

    @Test
    @DisplayName("Debe crear un recordatorio de evento y persistirlo")
    void execute_shouldCreateAndSaveEventReminder() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(1);

        EventReminder saved = EventReminder.builder()
                .id("reminder-001")
                .userId(USER_ID_1)
                .eventId(EVENT_ID_1)
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(false)
                .build();

        when(eventReminderRepository.save(any(EventReminder.class))).thenReturn(saved);

        EventReminder result = useCase.execute(USER_ID_1, EVENT_ID_1, eventDate);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("reminder-001");
        assertThat(result.getUserId()).isEqualTo(USER_ID_1);
        assertThat(result.getEventId()).isEqualTo(EVENT_ID_1);
        assertThat(result.getEventDate()).isEqualTo(eventDate);
        assertThat(result.isReminded24h()).isFalse();
        assertThat(result.isReminded1h()).isFalse();
    }

    @Test
    @DisplayName("Debe guardar el recordatorio con los datos correctos")
    void execute_shouldPersistReminderWithCorrectData() {
        LocalDateTime eventDate = LocalDateTime.now().plusHours(5);
        ArgumentCaptor<EventReminder> captor = ArgumentCaptor.forClass(EventReminder.class);

        EventReminder saved = EventReminder.builder()
                .id("reminder-002")
                .userId(USER_ID_2)
                .eventId(EVENT_ID_2)
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(false)
                .build();

        when(eventReminderRepository.save(captor.capture())).thenReturn(saved);

        useCase.execute(USER_ID_2, EVENT_ID_2, eventDate);

        EventReminder capturedReminder = captor.getValue();
        assertThat(capturedReminder.getUserId()).isEqualTo(USER_ID_2);
        assertThat(capturedReminder.getEventId()).isEqualTo(EVENT_ID_2);
        assertThat(capturedReminder.getEventDate()).isEqualTo(eventDate);
        assertThat(capturedReminder.isReminded24h()).isFalse();
        assertThat(capturedReminder.isReminded1h()).isFalse();

        verify(eventReminderRepository).save(any(EventReminder.class));
    }

    @Test
    @DisplayName("Debe retornar el recordatorio guardado")
    void execute_shouldReturnSavedReminder() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(7);

        EventReminder saved = EventReminder.builder()
                .id("reminder-003")
                .userId(USER_ID_3)
                .eventId(EVENT_ID_3)
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(false)
                .build();

        when(eventReminderRepository.save(any(EventReminder.class))).thenReturn(saved);

        EventReminder result = useCase.execute(USER_ID_3, EVENT_ID_3, eventDate);

        assertThat(result).isEqualTo(saved);
    }
}
