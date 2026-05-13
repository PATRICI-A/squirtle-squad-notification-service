package com.patricia.notification;

import com.patricia.notification.application.usecase.*;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetNotificationsUseCaseImplTest {

    private static final UUID USER_ID   = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID NOTIF_1   = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID NOTIF_2   = UUID.fromString("00000000-0000-0000-0000-000000000011");

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private GetNotificationsUseCaseImpl useCase;

    @Test
    @DisplayName("Debe retornar las notificaciones paginadas del repositorio")
    void execute_shouldReturnPagedNotifications() {
        List<Notification> expected = List.of(
                Notification.builder()
                        .id(NOTIF_1)
                        .userId(USER_ID)
                        .type(NotificationType.PARCHE_MESSAGE)
                        .channel(NotificationChannel.IN_APP)
                        .title("Mensaje 1")
                        .body("Cuerpo 1")
                        .read(false)
                        .createdAt(LocalDateTime.now())
                        .build(),
                Notification.builder()
                        .id(NOTIF_2)
                        .userId(USER_ID)
                        .type(NotificationType.CONNECTION_REQUEST)
                        .channel(NotificationChannel.IN_APP)
                        .title("Conexión")
                        .body("Cuerpo 2")
                        .read(true)
                        .createdAt(LocalDateTime.now().minusMinutes(5))
                        .build()
        );

        when(notificationRepository.findByUserIdPaged(USER_ID, 0, 20)).thenReturn(expected);

        List<Notification> result = useCase.execute(USER_ID, 0, 20);

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(expected);
        verify(notificationRepository).findByUserIdPaged(USER_ID, 0, 20);
    }

    @Test
    @DisplayName("Debe retornar lista vacía si el usuario no tiene notificaciones")
    void execute_shouldReturnEmptyList_whenNoNotifications() {
        when(notificationRepository.findByUserIdPaged(USER_ID_2, 0, 20)).thenReturn(List.of());

        List<Notification> result = useCase.execute(USER_ID_2, 0, 20);

        assertThat(result).isEmpty();
    }
}
