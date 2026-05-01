package com.patricia.notification.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "Recordatorio de evento registrado")
@Getter
@Builder
public class EventReminderResponse {

    @Schema(description = "ID del recordatorio", example = "6650a1f3e4b0c12d3a4f5678")
    private String id;

    @Schema(description = "ID del usuario", example = "user-123")
    private String userId;

    @Schema(description = "ID del evento", example = "evento-001")
    private String eventId;

    @Schema(description = "Fecha y hora del evento", example = "2026-05-02T14:00:00")
    private LocalDateTime eventDate;

    @Schema(description = "Si ya se envió recordatorio de 24h", example = "false")
    private boolean reminded24h;

    @Schema(description = "Si ya se envió recordatorio de 1h", example = "false")
    private boolean reminded1h;
}
