package com.patricia.notification;

import com.patricia.notification.application.usecase.*;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUnreadCountUseCaseImplTest {

    private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private GetUnreadCountUseCaseImpl useCase;

    @Test
    @DisplayName("Debe retornar el conteo de notificaciones no leídas")
    void execute_shouldReturnUnreadCount() {
        when(notificationRepository.countUnreadByUserId(USER_ID)).thenReturn(5);

        int result = useCase.execute(USER_ID);

        assertThat(result).isEqualTo(5);
        verify(notificationRepository).countUnreadByUserId(USER_ID);
    }

    @Test
    @DisplayName("Debe retornar 0 si no hay notificaciones no leídas")
    void execute_shouldReturnZero_whenNoUnread() {
        when(notificationRepository.countUnreadByUserId(USER_ID)).thenReturn(0);

        int result = useCase.execute(USER_ID);

        assertThat(result).isZero();
    }
}
