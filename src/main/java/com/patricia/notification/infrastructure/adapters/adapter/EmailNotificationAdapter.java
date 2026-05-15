package com.patricia.notification.infrastructure.adapters.adapter;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.ports.out.NotificationDeliveryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Delivery adapter that sends notifications via SMTP email.
 *
 * <p>Implements {@link NotificationDeliveryPort} for the {@link NotificationChannel#EMAIL} channel.
 * Email delivery failures are caught and logged without propagating the exception,
 * allowing the application to continue operating when the mail server is unavailable.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationAdapter implements NotificationDeliveryPort {

    private final JavaMailSender mailSender;

    /**
     * Sends the notification as a plain-text email to {@code notification.getRecipientEmail()}.
     * Logs a warning on delivery failure without rethrowing.
     *
     * @param notification the notification to deliver
     */
    @Override
    public void deliver(Notification notification) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(notification.getRecipientEmail());
            message.setSubject(notification.getTitle());
            message.setText(notification.getBody());

            mailSender.send(message);
            log.info("Notificación entregada via email a {}", notification.getRecipientEmail());
        } catch (Exception e) {
            log.error("Error enviando email a {}: {}",
                    notification.getRecipientEmail(), e.getMessage());
        }
    }

    @Override
    public NotificationChannel supportedChannel() {
        return NotificationChannel.EMAIL;
    }
}
