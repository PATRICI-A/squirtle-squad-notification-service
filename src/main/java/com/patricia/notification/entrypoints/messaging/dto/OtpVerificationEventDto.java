package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * Event DTO for OTP verification requests published by the auth service.
 *
 * <p>{@code userId} is optional: a null value indicates a pre-registration flow
 * where the user account does not yet exist. The consumer skips processing in that case.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerificationEventDto {

    /** ID of the user to notify. Null when the user has not completed registration yet. */
    private UUID userId;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{6}", message = "debe ser un código numérico de 6 dígitos")
    private String otpCode;
}
