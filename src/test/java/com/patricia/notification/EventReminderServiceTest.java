package com.patricia.notification;

import com.patricia.notification.application.service.EventReminderService;
import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.ports.out.EventReminderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventReminderServiceTest {

    @Mock
    private EventReminderRepository eventReminderRepository;

    @Mock
    private SendNotificationUseCase sendNotificationUseCase;

    @InjectMocks
    private EventReminderService eventReminderService;

    @Test
    @DisplayName("processReminders no debe hacer nada si no hay recordatorios pendientes")
    void processReminders_shouldDoNothing_whenNoPendingReminders() {
        when(eventReminderRepository.findPendingReminders()).thenReturn(List.of());

        eventReminderService.processReminders();

        verifyNoInteractions(sendNotificationUseCase);
        verify(eventReminderRepository, never()).save(any());
    }

    @Test
    @DisplayName("processReminders debe enviar notificación de 24h cuando el recordatorio lo necesita")
    void processReminders_shouldSend24hNotification_whenReminderIsIn24hWindow() {
        EventReminder reminder = EventReminder.builder()
                .id("r1")
                .userId("user-123")
                .eventId("event-456")
                .eventDate(LocalDateTime.now().plusMinutes(1440))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        when(eventReminderRepository.findPendingReminders()).thenReturn(List.of(reminder));

        eventReminderService.processReminders();

        verify(sendNotificationUseCase).execute(
                eq("user-123"),
                eq(NotificationType.EVENT_REMINDER),
                eq("Recordatorio de evento"),
                eq("Tu evento comienza en 24 horas"),
                eq("event-456")
        );
        verify(eventReminderRepository).save(reminder);
    }

    @Test
    @DisplayName("processReminders debe enviar notificación de 1h cuando el recordatorio lo necesita")
    void processReminders_shouldSend1hNotification_whenReminderIsIn1hWindow() {
        EventReminder reminder = EventReminder.builder()
                .id("r2")
                .userId("user-123")
                .eventId("event-456")
                .eventDate(LocalDateTime.now().plusMinutes(60))
                .reminded24h(true)
                .reminded1h(false)
                .build();

        when(eventReminderRepository.findPendingReminders()).thenReturn(List.of(reminder));

        eventReminderService.processReminders();

        verify(sendNotificationUseCase).execute(
                eq("user-123"),
                eq(NotificationType.EVENT_REMINDER),
                eq("Recordatorio de evento"),
                eq("Tu evento comienza en 1 hora"),
                eq("event-456")
        );
        verify(eventReminderRepository).save(reminder);
    }

    @Test
    @DisplayName("processReminders no debe enviar notificación si el recordatorio no está en ninguna ventana")
    void processReminders_shouldNotSendNotification_whenReminderIsOutsideAllWindows() {
        EventReminder reminder = EventReminder.builder()
                .id("r3")
                .userId("user-123")
                .eventId("event-789")
                .eventDate(LocalDateTime.now().plusDays(5))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        when(eventReminderRepository.findPendingReminders()).thenReturn(List.of(reminder));

        eventReminderService.processReminders();

        verifyNoInteractions(sendNotificationUseCase);
        verify(eventReminderRepository, never()).save(any());
    }

    @Test
    @DisplayName("processReminders debe procesar múltiples recordatorios independientemente")
    void processReminders_shouldProcessMultipleRemindersIndependently() {
        EventReminder reminder24h = EventReminder.builder()
                .id("r4")
                .userId("user-A")
                .eventId("event-A")
                .eventDate(LocalDateTime.now().plusMinutes(1440))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        EventReminder reminderFar = EventReminder.builder()
                .id("r5")
                .userId("user-B")
                .eventId("event-B")
                .eventDate(LocalDateTime.now().plusDays(7))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        when(eventReminderRepository.findPendingReminders())
                .thenReturn(List.of(reminder24h, reminderFar));

        eventReminderService.processReminders();

        verify(sendNotificationUseCase, times(1)).execute(
                eq("user-A"), any(), any(), any(), any());
        verify(eventReminderRepository, times(1)).save(any());
    }
}
