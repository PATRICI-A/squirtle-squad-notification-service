package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.application.mapper.NotificationMapper;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.ports.out.NotificationDeliveryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Delivery adapter that pushes notifications to connected clients via WebSocket (STOMP).
 *
 * <p>Implements {@link NotificationDeliveryPort} for the {@link NotificationChannel#IN_APP} channel.
 * Each notification is sent to a user-specific topic: {@code /topic/notifications/{userId}}.
 * If the user is not connected the message is silently dropped by the in-memory broker;
 * the notification remains available in MongoDB for later retrieval.</p>
 *
 * <p>Delivery failures are caught and logged without propagating the exception.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketNotificationAdapter implements NotificationDeliveryPort {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationMapper mapper;

    /**
     * Broadcasts the notification to the user-specific STOMP topic.
     *
     * @param notification the notification to deliver
     */
    @Override
    public void deliver(Notification notification) {
        String destination = "/topic/notifications/" + notification.getUserId();
        try {
            messagingTemplate.convertAndSend(destination, mapper.toResponse(notification));
            log.info("Notificación entregada via WebSocket al usuario {}",
                    notification.getUserId());
        } catch (Exception e) {
            log.error("Error entregando notificación via WebSocket al usuario {}: {}",
                    notification.getUserId(), e.getMessage());
        }
    }

    @Override
    public NotificationChannel supportedChannel() {
        return NotificationChannel.IN_APP;
    }
}
