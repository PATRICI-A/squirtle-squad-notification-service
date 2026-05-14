package com.patricia.notification.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Recordatorio de evento registrado")
@Getter
@Builder
public class EventReminderResponse {

    @Schema(description = "ID del recordatorio", example = "6650a1f3e4b0c12d3a4f5678")
    private String id;

    @Schema(description = "ID del usuario", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID userId;

    @Schema(description = "ID del evento", example = "550e8400-e29b-41d4-a716-446655440002")
    private UUID eventId;

    @Schema(description = "Fecha y hora del evento", example = "2026-05-02T14:00:00")
    private LocalDateTime eventDate;

    @Schema(description = "Si ya se envió recordatorio de 24h", example = "false")
    private boolean reminded24h;

    @Schema(description = "Si ya se envió recordatorio de 1h", example = "false")
    private boolean reminded1h;
}
