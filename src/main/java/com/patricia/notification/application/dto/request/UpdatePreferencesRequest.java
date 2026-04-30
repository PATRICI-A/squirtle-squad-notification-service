package com.patricia.notification.application.dto.request;

import com.patricia.notification.domain.model.enums.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdatePreferencesRequest {

    @NotNull
    private NotificationType type;

    @NotNull
    private Boolean enabled;
}