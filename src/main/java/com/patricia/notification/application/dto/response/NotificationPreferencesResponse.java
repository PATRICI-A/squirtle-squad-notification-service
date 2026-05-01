package com.patricia.notification.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "Preferencias de notificación del usuario")
@Getter
@Builder
public class NotificationPreferencesResponse {
    @Schema(description = "Notificaciones de solicitud de conexión", example = "true")
    private boolean connectionRequest;
    @Schema(description = "Notificaciones de mensajes en parches", example = "true")
    private boolean parcheMessage;
    @Schema(description = "Recordatorios de eventos", example = "true")
    private boolean eventReminder;
    @Schema(description = "Notificaciones de parches cercanos", example = "false")
    private boolean nearbyParche;
    @Schema(description = "Notificaciones de logros desbloqueados", example = "true")
    private boolean achievementUnlocked;
    @Schema(description = "Notificaciones de invitaciones a parches", example = "true")
    private boolean parcheInvitation;
}