package com.patricia.notification.entrypoints.rest.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * WebSocket controller that handles client connection handshakes for the
 * real-time notification channel.
 *
 * <p>Clients send their user ID to {@code /app/notifications.connect} after
 * establishing the STOMP connection. A confirmation message is broadcast to
 * {@code /topic/notifications/status}. Actual notifications are pushed to
 * user-specific destinations by {@link com.patricia.notification.infrastructure.adapters.adapter.WebSocketNotificationAdapter}.</p>
 */
@Controller
public class NotificationWebSocketController {

    /**
     * Acknowledges a client's connection to the notification channel.
     *
     * @param userId the user ID sent by the connecting client
     * @return a confirmation message broadcast to all subscribers of the status topic
     */
    @MessageMapping("/notifications.connect")
    @SendTo("/topic/notifications/status")
    public String handleConnect(String userId) {
        return "Usuario " + userId + " conectado al canal de notificaciones";
    }
}
