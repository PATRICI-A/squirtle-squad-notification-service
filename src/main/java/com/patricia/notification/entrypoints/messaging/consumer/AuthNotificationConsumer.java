package com.patricia.notification.entrypoints.messaging.consumer;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
import com.patricia.notification.entrypoints.messaging.dto.OtpVerificationEventDto;
import com.patricia.notification.entrypoints.messaging.dto.OtpResendEventDto;
import com.patricia.notification.entrypoints.messaging.dto.PasswordResetEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ consumer for authentication-related notification events.
 *
 * <p>Handles OTP verification, OTP resend, and password reset events published
 * by the auth service. Each handler validates the incoming DTO before processing;
 * malformed messages are rejected and routed to the dead-letter queue.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthNotificationConsumer {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final EventDtoValidator eventDtoValidator;

    /**
     * Sends an OTP verification notification.
     *
     * <p>If {@code userId} is null the event corresponds to a pre-registration flow
     * where the user does not yet exist in the system; the message is silently skipped.</p>
     *
     * @param event the OTP verification event from the auth service
     */
    @RabbitListener(queues = "${rabbitmq.queue.otp-verification}")
    public void handleOtpVerification(OtpVerificationEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: OTP verificación para {}", event.getEmail());
        if (event.getUserId() == null) return;
        sendNotificationUseCase.execute(
                event.getUserId(),
                NotificationType.OTP_VERIFICATION,
                "Código de verificación",
                "Tu código de verificación para PATRICIA es: " + event.getOtpCode()
                        + "\n\nEste código es válido por 10 minutos."
                        + "\n\nSi no solicitaste este código, ignora este mensaje.",
                null
        );
    }

    /**
     * Sends a resent OTP verification notification.
     *
     * @param event the OTP resend event from the auth service
     */
    @RabbitListener(queues = "${rabbitmq.queue.otp-resend}")
    public void handleOtpResend(OtpResendEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: OTP reenvío para {}", event.getEmail());
        sendNotificationUseCase.execute(
                event.getUserId(),
                NotificationType.OTP_VERIFICATION,
                "Nuevo código de verificación",
                "Tu nuevo código de verificación para PATRICIA es: " + event.getOtpCode()
                        + "\n\nEste código es válido por 10 minutos."
                        + "\n\nSi no solicitaste este código, ignora este mensaje.",
                null
        );
    }

    /**
     * Sends a password reset notification.
     *
     * @param event the password reset event from the auth service
     */
    @RabbitListener(queues = "${rabbitmq.queue.password-reset}")
    public void handlePasswordReset(PasswordResetEventDto event) {
        eventDtoValidator.validate(event);
        log.info("Evento recibido: recuperación de contraseña para {}", event.getEmail());
        sendNotificationUseCase.execute(
                event.getUserId(),
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
