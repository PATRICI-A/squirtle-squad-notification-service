package com.patricia.notification.infrastructure.external;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmClient {

    private final FirebaseMessaging firebaseMessaging;

    public void sendPush(String token, String title, String body) {
        Message message = Message.builder()
                .setToken(token)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();
        try {
            String response = firebaseMessaging.send(message);
            log.info("Push enviada exitosamente: {}", response);
        } catch (Exception e) {
            log.error("Error en FCM: {}", e.getMessage());
            throw new RuntimeException("Fallo en el envío FCM", e);
        }
    }
}