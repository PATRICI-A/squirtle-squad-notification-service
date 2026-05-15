package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerificationEventDto {
    private UUID userId;

    @NotBlank
    private String email;

    @NotBlank
    private String otpCode;
}