package com.patricia.notification;

import com.patricia.notification.application.mapper.NotificationMapper;
import com.patricia.notification.application.dto.response.NotificationResponse;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.infrastructure.adapters.adapter.WebSocketNotificationAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebSocketNotificationAdapterTest {

    private static final UUID USER_ID_1  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID NOTIF_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID NOTIF_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000011");

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private NotificationMapper mapper;

    @InjectMocks
    private WebSocketNotificationAdapter adapter;

    @Test
    @DisplayName("deliver debe enviar notificación al topic del usuario via WebSocket")
    void deliver_shouldSendToUserTopic() {
        Notification notification = Notification.builder()
                .id(NOTIF_ID_1)
                .userId(USER_ID_1)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Mensaje")
                .body("Tienes un nuevo mensaje")
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        NotificationResponse response = NotificationResponse.builder()
                .id(NOTIF_ID_1)
                .userId(USER_ID_1)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Mensaje")
                .body("Tienes un nuevo mensaje")
                .build();

        when(mapper.toResponse(notification)).thenReturn(response);

        adapter.deliver(notification);

        verify(messagingTemplate).convertAndSend(
                eq("/topic/notifications/" + USER_ID_1),
                eq(response)
        );
    }

    @Test
    @DisplayName("deliver no debe propagar excepción cuando SimpMessagingTemplate falla")
    void deliver_shouldNotPropagateException_whenMessagingTemplateFails() {
        Notification notification = Notification.builder()
                .id(NOTIF_ID_2)
                .userId(USER_ID_2)
                .type(NotificationType.CONNECTION_REQUEST)
                .channel(NotificationChannel.IN_APP)
                .title("Conexión")
                .body("Quieren conectarse")
                .read(false)
                .build();

        NotificationResponse response = NotificationResponse.builder()
                .id(NOTIF_ID_2)
                .build();

        when(mapper.toResponse(notification)).thenReturn(response);
        doThrow(new RuntimeException("WebSocket error"))
                .when(messagingTemplate).convertAndSend(anyString(), (Object) any());

        adapter.deliver(notification);

        verify(messagingTemplate).convertAndSend(anyString(), (Object) any());
    }

    @Test
    @DisplayName("supportedChannel debe retornar IN_APP")
    void supportedChannel_shouldReturnInApp() {
        assertThat(adapter.supportedChannel()).isEqualTo(NotificationChannel.IN_APP);
    }
}
