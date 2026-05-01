package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.FcmToken;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.ports.out.PushDeliveryPort;
import com.patricia.notification.infrastructure.external.FcmClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmPushAdapter implements PushDeliveryPort {

    private final FcmClient fcmClient;

    @Override
    public void send(Notification notification, FcmToken token) {
        try {
            fcmClient.sendPush(
                    token.getToken(),
                    notification.getTitle(),
                    notification.getBody()
            );
        } catch (Exception e) {
            log.error("Error enviando push al token {} del usuario {}: {}",
                    token.getToken(), notification.getUserId(), e.getMessage());
        }
    }
}