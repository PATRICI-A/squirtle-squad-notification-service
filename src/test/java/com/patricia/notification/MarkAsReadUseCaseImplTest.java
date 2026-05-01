package com.patricia.notification;

import com.patricia.notification.application.usecase.*;
import com.patricia.notification.domain.exceptions.NotificationNotFoundException;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarkAsReadUseCaseImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private MarkAsReadUseCaseImpl useCase;

    @Test
    @DisplayName("executeSingle: debe marcar la notificación como leída y persistirla")
    void executeSingle_shouldMarkAsReadAndSave() {
        // Arrange
        Notification notification = Notification.builder()
                .id("notif-001")
                .userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Mensaje")
                .body("Hola")
                .read(false)
                .build();

        when(notificationRepository.findById("notif-001"))
                .thenReturn(Optional.of(notification));
        when(notificationRepository.save(any())).thenReturn(notification);

        useCase.executeSingle("notif-001", "user-123");

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        assertThat(captor.getValue().isRead()).isTrue();
    }

    @Test
    @DisplayName("executeSingle: debe lanzar NotificationNotFoundException si no existe")
    void executeSingle_shouldThrow_whenNotificationNotFound() {
        when(notificationRepository.findById("notif-inexistente"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                useCase.executeSingle("notif-inexistente", "user-123"))
                .isInstanceOf(NotificationNotFoundException.class);

        verify(notificationRepository, never()).save(any());
    }



    @Test
    @DisplayName("executeAll: debe delegar al repositorio para marcar todas como leídas")
    void executeAll_shouldCallRepositoryMarkAll() {
        useCase.executeAll("user-123");

        verify(notificationRepository).markAllAsReadByUserId("user-123");
        verifyNoMoreInteractions(notificationRepository);
    }
}