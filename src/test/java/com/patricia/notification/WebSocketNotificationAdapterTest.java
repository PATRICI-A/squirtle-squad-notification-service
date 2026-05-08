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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebSocketNotificationAdapterTest {

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
                .id("notif-001")
                .userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Mensaje")
                .body("Tienes un nuevo mensaje")
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        NotificationResponse response = NotificationResponse.builder()
                .id("notif-001")
                .userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Mensaje")
                .body("Tienes un nuevo mensaje")
                .build();

        when(mapper.toResponse(notification)).thenReturn(response);

        adapter.deliver(notification);

        verify(messagingTemplate).convertAndSend(
                eq("/topic/notifications/user-123"),
                eq(response)
        );
    }

    @Test
    @DisplayName("deliver no debe propagar excepción cuando SimpMessagingTemplate falla")
    void deliver_shouldNotPropagateException_whenMessagingTemplateFails() {
        Notification notification = Notification.builder()
                .id("notif-002")
                .userId("user-456")
                .type(NotificationType.CONNECTION_REQUEST)
                .channel(NotificationChannel.IN_APP)
                .title("Conexión")
                .body("Quieren conectarse")
                .read(false)
                .build();

        NotificationResponse response = NotificationResponse.builder()
                .id("notif-002")
                .build();

        when(mapper.toResponse(notification)).thenReturn(response);
        doThrow(new RuntimeException("WebSocket error"))
                .when(messagingTemplate).convertAndSend(anyString(), (Object) any());

        // Should not throw
        adapter.deliver(notification);

        verify(messagingTemplate).convertAndSend(anyString(), (Object) any());
    }

    @Test
    @DisplayName("supportedChannel debe retornar IN_APP")
    void supportedChannel_shouldReturnInApp() {
        assertThat(adapter.supportedChannel()).isEqualTo(NotificationChannel.IN_APP);
    }
}
