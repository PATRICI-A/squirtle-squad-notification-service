package com.patricia.notification.application.dto.request;

import com.patricia.notification.domain.validation.MaxFutureDays;
import com.patricia.notification.domain.validation.NotificationRules;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Request payload for registering an event reminder.
 *
 * <p>Once registered, the scheduler will send notifications to the user
 * 24 hours and 1 hour before the event date.</p>
 */
@Schema(description = "Request payload for registering an event reminder")
@Getter
public class CreateEventReminderRequest {

    @Schema(description = "ID of the user who will receive the reminders", example = "550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID userId;

    @Schema(description = "ID of the event to be reminded about", example = "550e8400-e29b-41d4-a716-446655440001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID eventId;

    @Schema(description = "Scheduled date and time of the event — must be in the future and within one year", example = "2026-05-02T14:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Future
    @MaxFutureDays(NotificationRules.MAX_EVENT_FUTURE_DAYS)
    private LocalDateTime eventDate;
}
