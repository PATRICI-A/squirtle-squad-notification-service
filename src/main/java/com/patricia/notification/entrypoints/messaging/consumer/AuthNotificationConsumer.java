package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.entrypoints.messaging.dto.OtpVerificationEventDto;
import com.patricia.notification.entrypoints.messaging.dto.OtpResendEventDto;
import com.patricia.notification.entrypoints.messaging.dto.PasswordResetEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;

    @RabbitListener(queues = "${rabbitmq.queue.otp-verification}")
    public void handleOtpVerification(OtpVerificationEventDto event) {
        log.info("Evento recibido: OTP verificación para {}", event.getEmail());
        sendNotificationUseCase.execute(
                event.getEmail(),
                NotificationType.OTP_VERIFICATION,
                "Código de verificación",
                "Tu código de verificación para PATRICIA es: " + event.getOtpCode()
                        + "\n\nEste código es válido por 10 minutos."
                        + "\n\nSi no solicitaste este código, ignora este mensaje.",
                null
        );
    }

    @RabbitListener(queues = "${rabbitmq.queue.otp-resend}")
    public void handleOtpResend(OtpResendEventDto event) {
        log.info("Evento recibido: OTP reenvío para {}", event.getEmail());
        sendNotificationUseCase.execute(
                event.getEmail(),
                NotificationType.OTP_VERIFICATION,
                "Nuevo código de verificación",
                "Tu nuevo código de verificación para PATRICIA es: " + event.getOtpCode()
                        + "\n\nEste código es válido por 10 minutos."
                        + "\n\nSi no solicitaste este código, ignora este mensaje.",
                null
        );
    }

    @RabbitListener(queues = "${rabbitmq.queue.password-reset}")
    public void handlePasswordReset(PasswordResetEventDto event) {
        log.info("Evento recibido: recuperación de contraseña para {}", event.getEmail());
        sendNotificationUseCase.execute(
                event.getEmail(),
                NotificationType.PASSWORD_RESET,
                "Recuperación de contraseña",
                "Recibimos una solicitud para restablecer tu contraseña en PATRICIA."
                        + "\n\nTu código de recuperación es: " + event.getResetCode()
                        + "\n\nEste código es válido por 10 minutos."
                        + "\n\nSi no solicitaste esto, ignora este mensaje. Tu contraseña no cambiará.",
                event.getUserId()
        );
    }
}