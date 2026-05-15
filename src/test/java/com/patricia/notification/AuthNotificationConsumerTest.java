package com.patricia.notification;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.validation.EventDtoValidator;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthNotificationConsumerTest {

    private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Mock
    private SendNotificationUseCase sendNotificationUseCase;

    @Mock
    private EventDtoValidator eventDtoValidator;

    @InjectMocks
    private AuthNotificationConsumer consumer;

    @Test
    @DisplayName("handleOtpVerification debe enviar notificación OTP con código")
    void handleOtpVerification_shouldSendOtpNotification() {
        OtpVerificationEventDto event = OtpVerificationEventDto.builder()
                .userId(USER_ID)
                .email("user@example.com")
                .otpCode("123456")
                .build();

        consumer.handleOtpVerification(event);

        verify(sendNotificationUseCase).execute(
                eq(USER_ID),
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
                .userId(USER_ID)
                .email("user@example.com")
                .otpCode("654321")
                .build();

        consumer.handleOtpResend(event);

        verify(sendNotificationUseCase).execute(
                eq(USER_ID),
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
                .userId(USER_ID)
                .build();

        consumer.handlePasswordReset(event);

        verify(sendNotificationUseCase).execute(
                eq(USER_ID),
                eq(NotificationType.PASSWORD_RESET),
                eq("Recuperación de contraseña"),
                contains("RESET-ABC-001"),
                eq(USER_ID)
        );
    }
}
