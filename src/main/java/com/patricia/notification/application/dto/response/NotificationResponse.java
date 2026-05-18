package com.patricia.notification.application.dto.response;

import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response payload representing a single notification.
 */
@Schema(description = "Notification data returned by the API")
@Getter
@Builder
public class NotificationResponse {

    @Schema(description = "Unique notification identifier", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "ID of the recipient user", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID userId;

    @Schema(description = "Notification type", example = "PARCHE_MESSAGE")
    private NotificationType type;

    @Schema(description = "Delivery channel used for this notification", example = "IN_APP")
    private NotificationChannel channel;

    @Schema(description = "Short summary shown in notification lists", example = "New message in your parche")
    private String title;

    @Schema(description = "Full notification message", example = "Juan sent a message in 'Friday Parche'")
    private String body;

    @Schema(description = "Whether the recipient has viewed this notification", example = "false")
    private boolean read;

    @Schema(description = "ID of the entity that triggered this notification (parche, event, etc.)", example = "550e8400-e29b-41d4-a716-446655440002")
    private UUID referenceId;

    @Schema(description = "Timestamp when the notification was created (ISO 8601)", example = "2026-05-01T10:30:00")
    private LocalDateTime createdAt;
}
