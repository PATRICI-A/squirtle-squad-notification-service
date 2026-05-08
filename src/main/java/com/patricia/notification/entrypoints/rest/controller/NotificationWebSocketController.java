package com.patricia.notification.entrypoints.rest.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationWebSocketController {

    @MessageMapping("/notifications.connect")
    @SendTo("/topic/notifications/status")
    public String handleConnect(String userId) {
        return "Usuario " + userId + " conectado al canal de notificaciones";
    }
}