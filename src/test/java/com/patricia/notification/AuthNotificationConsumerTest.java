package com.patricia.notification;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.entrypoints.messaging.consumer.AuthNotificationConsumer;
import com.patricia.notification.entrypoints.messaging.dto.OtpResendEventDto;
import com.patricia.notification.entrypoints.messaging.dto.OtpVerificationEventDto;
import com.patricia.notification.entrypoints.messaging.dto.PasswordResetEventDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthNotificationConsumerTest {

    @Mock
    private SendNotificationUseCase sendNotificationUseCase;

    @InjectMocks
    private AuthNotificationConsumer consumer;

    @Test
    @DisplayName("handleOtpVerification debe enviar notificación OTP con código")
    void handleOtpVerification_shouldSendOtpNotification() {
        OtpVerificationEventDto event = OtpVerificationEventDto.builder()
                .email("user@example.com")
                .otpCode("123456")
                .build();

        consumer.handleOtpVerification(event);

        verify(sendNotificationUseCase).execute(
                eq("user@example.com"),
                eq(NotificationType.OTP_VERIFICATION),
                eq("Código de verificación"),
                contains("123456"),
                isNull()
        );
    }

    @Test
    @DisplayName("handleOtpResend debe enviar nuevo código OTP")
    void handleOtpResend_shouldSendNewOtpNotification() {
        OtpResendEventDto event = OtpResendEventDto.builder()
                .email("user@example.com")
                .otpCode("654321")
                .build();

        consumer.handleOtpResend(event);

        verify(sendNotificationUseCase).execute(
                eq("user@example.com"),
                eq(NotificationType.OTP_VERIFICATION),
                eq("Nuevo código de verificación"),
                contains("654321"),
                isNull()
        );
    }

    @Test
    @DisplayName("handlePasswordReset debe enviar notificación con código de recuperación")
    void handlePasswordReset_shouldSendPasswordResetNotification() {
        PasswordResetEventDto event = PasswordResetEventDto.builder()
                .email("user@example.com")
                .resetCode("RESET-ABC-001")
                .userId("user-123")
                .build();

        consumer.handlePasswordReset(event);

        verify(sendNotificationUseCase).execute(
                eq("user@example.com"),
                eq(NotificationType.PASSWORD_RESET),
                eq("Recuperación de contraseña"),
                contains("RESET-ABC-001"),
                eq("user-123")
        );
    }
}
