package com.patricia.notification.application.dto.request;

import com.patricia.notification.domain.model.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * Request payload for enabling or disabling a specific notification type.
 */
@Schema(description = "Request payload for updating a notification preference")
@Getter
public class UpdatePreferencesRequest {

    @Schema(description = "Notification type to configure", example = "PARCHE_MESSAGE")
    @NotNull
    private NotificationType type;

    @Schema(description = "true to enable, false to disable", example = "false")
    @NotNull
    private Boolean enabled;
}
