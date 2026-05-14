package com.patricia.notification;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.infrastructure.adapters.adapter.EmailNotificationAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailNotificationAdapterTest {

    private static final UUID NOTIF_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID NOTIF_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID USER_ID    = UUID.fromString("00000000-0000-0000-0000-000000000010");

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailNotificationAdapter adapter;

    @Test
    @DisplayName("deliver debe enviar email con el título y cuerpo de la notificación")
    void deliver_shouldSendEmailWithNotificationContent() {
        Notification notification = Notification.builder()
                .id(NOTIF_ID_1)
                .userId(USER_ID)
                .recipientEmail("user@example.com")
                .type(NotificationType.OTP_VERIFICATION)
                .channel(NotificationChannel.EMAIL)
                .title("Código de verificación")
                .body("Tu código es: 123456")
                .read(false)
                .build();

        adapter.deliver(notification);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("deliver no debe propagar excepción cuando JavaMailSender falla")
    void deliver_shouldNotPropagateException_whenMailSenderFails() {
        Notification notification = Notification.builder()
                .id(NOTIF_ID_2)
                .userId(USER_ID)
                .recipientEmail("user@example.com")
                .type(NotificationType.PASSWORD_RESET)
                .channel(NotificationChannel.EMAIL)
                .title("Reset")
                .body("Tu código de reset")
                .read(false)
                .build();

        doThrow(new RuntimeException("SMTP error")).when(mailSender).send(any(SimpleMailMessage.class));

        adapter.deliver(notification);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("supportedChannel debe retornar EMAIL")
    void supportedChannel_shouldReturnEmail() {
        assertThat(adapter.supportedChannel()).isEqualTo(NotificationChannel.EMAIL);
    }
}
