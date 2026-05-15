package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * Event DTO for OTP resend requests published by the auth service.
 *
 * <p>Unlike {@link OtpVerificationEventDto}, {@code userId} is required here
 * because the user must already exist to request a code resend.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpResendEventDto {

    @NotNull
    private UUID userId;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{6}", message = "debe ser un código numérico de 6 dígitos")
    private String otpCode;
}
