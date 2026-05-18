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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarkAsReadUseCaseImplTest {

    private static final UUID NOTIF_ID       = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID NOTIF_ID_MISS  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID USER_ID        = UUID.fromString("00000000-0000-0000-0000-000000000010");

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private MarkAsReadUseCaseImpl useCase;

    @Test
    @DisplayName("executeSingle: debe marcar la notificación como leída y persistirla")
    void executeSingle_shouldMarkAsReadAndSave() {
        Notification notification = Notification.builder()
                .id(NOTIF_ID)
                .userId(USER_ID)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Mensaje")
                .body("Hola")
                .read(false)
                .build();

        when(notificationRepository.findById(NOTIF_ID)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(any())).thenReturn(notification);

        useCase.executeSingle(NOTIF_ID, USER_ID);

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(captor.capture());
        assertThat(captor.getValue().isRead()).isTrue();
    }

    @Test
    @DisplayName("executeSingle: debe lanzar NotificationNotFoundException si no existe")
    void executeSingle_shouldThrow_whenNotificationNotFound() {
        when(notificationRepository.findById(NOTIF_ID_MISS)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.executeSingle(NOTIF_ID_MISS, USER_ID))
                .isInstanceOf(NotificationNotFoundException.class);

        verify(notificationRepository, never()).save(any());
    }

    @Test
    @DisplayName("executeAll: debe delegar al repositorio para marcar todas como leídas")
    void executeAll_shouldCallRepositoryMarkAll() {
        useCase.executeAll(USER_ID);

        verify(notificationRepository).markAllAsReadByUserId(USER_ID);
        verifyNoMoreInteractions(notificationRepository);
    }
}
