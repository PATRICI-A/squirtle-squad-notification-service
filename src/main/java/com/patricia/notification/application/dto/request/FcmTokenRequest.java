package com.patricia.notification.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FcmTokenRequest {

    @NotBlank
    private String token;

    @NotBlank
    private String device;
}