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
 * Event DTO for password reset requests published by the auth service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetEventDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "\\d{6}", message = "debe ser un código numérico de 6 dígitos")
    private String resetCode;

    @NotNull
    private UUID userId;
}
