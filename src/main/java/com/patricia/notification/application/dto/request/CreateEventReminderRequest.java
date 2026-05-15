package com.patricia.notification.application.dto.request;

import com.patricia.notification.domain.validation.MaxFutureDays;
import com.patricia.notification.domain.validation.NotificationRules;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Request para registrar un recordatorio de evento")
@Getter
public class CreateEventReminderRequest {

    @Schema(description = "ID del usuario", example = "550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID userId;

    @Schema(description = "ID del evento", example = "550e8400-e29b-41d4-a716-446655440001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID eventId;

    @Schema(description = "Fecha y hora del evento (futura)", example = "2026-05-02T14:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Future
    @MaxFutureDays(NotificationRules.MAX_EVENT_FUTURE_DAYS)
    private LocalDateTime eventDate;
}
