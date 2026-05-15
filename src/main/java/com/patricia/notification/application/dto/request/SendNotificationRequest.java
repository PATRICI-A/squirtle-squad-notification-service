package com.patricia.notification.application.dto.request;

import com.patricia.notification.domain.model.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import com.patricia.notification.domain.validation.NotificationRules;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Schema(description = "Request para crear una notificación")
@Getter
public class SendNotificationRequest {

    @Schema(description = "ID del usuario destinatario", example = "550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private UUID userId;

    @Schema(description = "Tipo de notificación", example = "PARCHE_MESSAGE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private NotificationType type;

    @Schema(description = "Título de la notificación", example = "Nuevo mensaje en tu parche", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = NotificationRules.MAX_TITLE_LENGTH)
    private String title;

    @Schema(description = "Cuerpo del mensaje", example = "Juan te envió un mensaje en 'Parche del viernes'", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(max = NotificationRules.MAX_BODY_LENGTH)
    private String body;

    @Schema(description = "ID del objeto relacionado (parche, evento, etc.)", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID referenceId;
}