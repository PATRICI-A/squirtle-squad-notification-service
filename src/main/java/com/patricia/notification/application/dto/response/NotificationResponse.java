package com.patricia.notification.application.dto.response;

import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Notificación del usuario")
@Getter
@Builder
public class NotificationResponse {
    @Schema(description = "ID único de la notificación", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;
    @Schema(description = "ID del usuario destinatario", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID userId;
    @Schema(description = "Tipo de notificación", example = "PARCHE_MESSAGE")
    private NotificationType type;
    @Schema(description = "Canal de entrega", example = "IN_APP")
    private NotificationChannel channel;
    @Schema(description = "Título de la notificación", example = "Nuevo mensaje en tu parche")
    private String title;
    @Schema(description = "Cuerpo del mensaje", example = "Juan te envió un mensaje en 'Parche del viernes'")
    private String body;
    @Schema(description = "Si el usuario ya leyó la notificación", example = "false")
    private boolean read;
    @Schema(description = "ID de referencia al objeto relacionado (parche, evento, etc.)", example = "550e8400-e29b-41d4-a716-446655440002")
    private UUID referenceId;
    @Schema(description = "Fecha y hora de creación", example = "2026-05-01T10:30:00")
    private LocalDateTime createdAt;
}