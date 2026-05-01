package com.patricia.notification.application.dto.request;

import com.patricia.notification.domain.model.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "Request para actualizar una preferencia de notificación")
@Getter
public class UpdatePreferencesRequest {

    @Schema(description = "Tipo de notificación a configurar", example = "PARCHE_MESSAGE")
    @NotNull
    private NotificationType type;

    @Schema(description = "true para habilitar, false para deshabilitar", example = "false")
    @NotNull
    private Boolean enabled;
}