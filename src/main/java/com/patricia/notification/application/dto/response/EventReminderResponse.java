package com.patricia.notification.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response payload representing a registered event reminder.
 */
@Schema(description = "Event reminder data returned by the API")
@Getter
@Builder
public class EventReminderResponse {

    @Schema(description = "Unique reminder identifier (MongoDB ObjectId)", example = "6650a1f3e4b0c12d3a4f5678")
    private String id;

    @Schema(description = "ID of the user who will receive the reminders", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID userId;

    @Schema(description = "ID of the event being tracked", example = "550e8400-e29b-41d4-a716-446655440002")
    private UUID eventId;

    @Schema(description = "Scheduled date and time of the event", example = "2026-05-02T14:00:00")
    private LocalDateTime eventDate;

    @Schema(description = "Whether the 24-hour reminder has already been sent", example = "false")
    private boolean reminded24h;

    @Schema(description = "Whether the 1-hour reminder has already been sent", example = "false")
    private boolean reminded1h;
}
