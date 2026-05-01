package com.patricia.notification.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "Request para registrar un recordatorio de evento")
@Getter
public class CreateEventReminderRequest {

    @Schema(description = "ID del usuario", example = "user-123", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String userId;

    @Schema(description = "ID del evento", example = "evento-001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String eventId;

    @Schema(description = "Fecha y hora del evento (futura)", example = "2026-05-02T14:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Future
    private LocalDateTime eventDate;
}
