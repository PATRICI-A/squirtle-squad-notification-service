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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SocialNotificationConsumerTest {

    private static final UUID TARGET_USER_ID  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID REQUESTER_ID    = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Mock
    private SendNotificationUseCase sendNotificationUseCase;

    @InjectMocks
    private SocialNotificationConsumer consumer;

    @Test
    @DisplayName("handleConnectionRequest debe enviar notificación de solicitud de conexión")
    void handleConnectionRequest_shouldSendConnectionNotification() {
        ConnectionRequestEventDto event = ConnectionRequestEventDto.builder()
                .targetUserId(TARGET_USER_ID)
                .requesterUserName("Maria")
                .requesterId(REQUESTER_ID)
                .build();

        consumer.handleConnectionRequest(event);

        verify(sendNotificationUseCase).execute(
                eq(TARGET_USER_ID),
                eq(NotificationType.CONNECTION_REQUEST),
                eq("Nueva solicitud de conexión"),
                contains("Maria"),
                eq(REQUESTER_ID)
        );
    }
}
