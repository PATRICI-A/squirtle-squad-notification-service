package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.application.mapper.NotificationMapper;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.ports.out.NotificationDeliveryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketNotificationAdapter implements NotificationDeliveryPort {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationMapper mapper;

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
}