package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpResendEventDto {
    @NotNull
    private UUID userId;

    @NotBlank
    private String email;

    @NotBlank
    private String otpCode;
}
