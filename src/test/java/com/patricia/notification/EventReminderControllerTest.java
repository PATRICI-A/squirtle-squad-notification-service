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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventReminderControllerTest {

    @Mock
    private CreateEventReminderUseCase createEventReminderUseCase;

    @InjectMocks
    private EventReminderController controller;

    @Test
    @DisplayName("createReminder debe retornar 201 con el recordatorio creado")
    void createReminder_shouldReturn201WithReminderResponse() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(1);

        CreateEventReminderRequest request = mock(CreateEventReminderRequest.class);
        when(request.getUserId()).thenReturn("user-123");
        when(request.getEventId()).thenReturn("event-456");
        when(request.getEventDate()).thenReturn(eventDate);

        EventReminder reminder = EventReminder.builder()
                .id("reminder-001")
                .userId("user-123")
                .eventId("event-456")
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(false)
                .build();

        when(createEventReminderUseCase.execute("user-123", "event-456", eventDate))
                .thenReturn(reminder);

        ResponseEntity<EventReminderResponse> result = controller.createReminder(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isEqualTo("reminder-001");
        assertThat(result.getBody().getUserId()).isEqualTo("user-123");
        assertThat(result.getBody().getEventId()).isEqualTo("event-456");
        assertThat(result.getBody().getEventDate()).isEqualTo(eventDate);
        assertThat(result.getBody().isReminded24h()).isFalse();
        assertThat(result.getBody().isReminded1h()).isFalse();
    }

    @Test
    @DisplayName("createReminder debe llamar al use case con los datos del request")
    void createReminder_shouldCallUseCaseWithRequestData() {
        LocalDateTime eventDate = LocalDateTime.now().plusHours(48);

        CreateEventReminderRequest request = mock(CreateEventReminderRequest.class);
        when(request.getUserId()).thenReturn("user-789");
        when(request.getEventId()).thenReturn("event-999");
        when(request.getEventDate()).thenReturn(eventDate);

        EventReminder reminder = EventReminder.builder()
                .id("reminder-002")
                .userId("user-789")
                .eventId("event-999")
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(false)
                .build();

        when(createEventReminderUseCase.execute("user-789", "event-999", eventDate))
                .thenReturn(reminder);

        controller.createReminder(request);

        verify(createEventReminderUseCase).execute("user-789", "event-999", eventDate);
    }
}
