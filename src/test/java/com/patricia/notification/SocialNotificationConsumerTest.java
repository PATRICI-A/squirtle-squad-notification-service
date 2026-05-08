package com.patricia.notification;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.entrypoints.messaging.consumer.SocialNotificationConsumer;
import com.patricia.notification.entrypoints.messaging.dto.ConnectionRequestEventDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SocialNotificationConsumerTest {

    @Mock
    private SendNotificationUseCase sendNotificationUseCase;

    @InjectMocks
    private SocialNotificationConsumer consumer;

    @Test
    @DisplayName("handleConnectionRequest debe enviar notificación de solicitud de conexión")
    void handleConnectionRequest_shouldSendConnectionNotification() {
        ConnectionRequestEventDto event = ConnectionRequestEventDto.builder()
                .targetUserId("user-target")
                .requesterUserName("Maria")
                .requesterId("user-requester-001")
                .build();

        consumer.handleConnectionRequest(event);

        verify(sendNotificationUseCase).execute(
                eq("user-target"),
                eq(NotificationType.CONNECTION_REQUEST),
                eq("Nueva solicitud de conexión"),
                contains("Maria"),
                eq("user-requester-001")
        );
    }
}
