package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.ports.out.NotificationDeliveryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationAdapter implements NotificationDeliveryPort {

    private final JavaMailSender mailSender;

    @Override
    public void deliver(Notification notification) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getUserId());
            message.setSubject(notification.getTitle());
            message.setText(notification.getBody());

            mailSender.send(message);
            log.info("Notificación entregada via email a {}", notification.getUserId());
        } catch (Exception e) {
            log.error("Error enviando email a {}: {}",
                    notification.getUserId(), e.getMessage());
        }
    }

    @Override
    public NotificationChannel supportedChannel() {
        return NotificationChannel.EMAIL;
    }
}