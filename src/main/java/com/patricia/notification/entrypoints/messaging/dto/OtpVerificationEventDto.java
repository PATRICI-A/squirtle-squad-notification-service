package com.patricia.notification.entrypoints.messaging.dto;

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
    private String email;
    private String otpCode;
}