package com.patricia.notification.application.dto.request;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.validation.NotificationRules;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

/**
 * Request payload for creating and delivering a notification to a specific user.
 */
@Schema(description = "Request payload for creating a notification")
@Getter
public class SendNotificationRequest {

    @Schema(description = "ID of the recipient user", example = "550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID userId;

    @Schema(description = "Notification type", example = "PARCHE_MESSAGE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private NotificationType type;

    @Schema(description = "Short summary shown in notification lists (max 80 characters)", example = "New message in your parche", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = NotificationRules.MAX_TITLE_LENGTH)
    private String title;

    @Schema(description = "Full notification message (max 200 characters)", example = "Juan sent a message in 'Friday Parche'", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = NotificationRules.MAX_BODY_LENGTH)
    private String body;

    @Schema(description = "ID of the entity that triggered this notification (parche, event, etc.)", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID referenceId;
}
