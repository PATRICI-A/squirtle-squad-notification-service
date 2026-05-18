package com.patricia.notification;

import com.patricia.notification.application.dto.request.CreateEventReminderRequest;
import com.patricia.notification.application.dto.response.EventReminderResponse;
import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.domain.ports.in.CreateEventReminderUseCase;
import com.patricia.notification.entrypoints.rest.controller.EventReminderController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventReminderControllerTest {

    private static final UUID USER_ID_1  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID EVENT_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID EVENT_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000011");

    @Mock
    private CreateEventReminderUseCase createEventReminderUseCase;

    @InjectMocks
    private EventReminderController controller;

    @Test
    @DisplayName("createReminder debe retornar 201 con el recordatorio creado")
    void createReminder_shouldReturn201WithReminderResponse() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(1);

        CreateEventReminderRequest request = mock(CreateEventReminderRequest.class);
        when(request.getUserId()).thenReturn(USER_ID_1);
        when(request.getEventId()).thenReturn(EVENT_ID_1);
        when(request.getEventDate()).thenReturn(eventDate);

        EventReminder reminder = EventReminder.builder()
                .id("reminder-001")
                .userId(USER_ID_1)
                .eventId(EVENT_ID_1)
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(false)
                .build();

        when(createEventReminderUseCase.execute(USER_ID_1, EVENT_ID_1, eventDate))
                .thenReturn(reminder);

        ResponseEntity<EventReminderResponse> result = controller.createReminder(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo("reminder-001");
        assertThat(result.getBody().getUserId()).isEqualTo(USER_ID_1);
        assertThat(result.getBody().getEventId()).isEqualTo(EVENT_ID_1);
        assertThat(result.getBody().getEventDate()).isEqualTo(eventDate);
        assertThat(result.getBody().isReminded24h()).isFalse();
        assertThat(result.getBody().isReminded1h()).isFalse();
    }

    @Test
    @DisplayName("createReminder debe llamar al use case con los datos del request")
    void createReminder_shouldCallUseCaseWithRequestData() {
        LocalDateTime eventDate = LocalDateTime.now().plusHours(48);

        CreateEventReminderRequest request = mock(CreateEventReminderRequest.class);
        when(request.getUserId()).thenReturn(USER_ID_2);
        when(request.getEventId()).thenReturn(EVENT_ID_2);
        when(request.getEventDate()).thenReturn(eventDate);

        EventReminder reminder = EventReminder.builder()
                .id("reminder-002")
                .userId(USER_ID_2)
                .eventId(EVENT_ID_2)
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(false)
                .build();

        when(createEventReminderUseCase.execute(USER_ID_2, EVENT_ID_2, eventDate))
                .thenReturn(reminder);

        controller.createReminder(request);

        verify(createEventReminderUseCase).execute(USER_ID_2, EVENT_ID_2, eventDate);
    }
}
