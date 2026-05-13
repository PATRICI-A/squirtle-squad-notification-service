package com.patricia.notification.entrypoints.rest.controller;

import com.patricia.notification.application.dto.request.SendNotificationRequest;
import com.patricia.notification.application.dto.request.UpdatePreferencesRequest;
import com.patricia.notification.application.dto.response.NotificationPreferencesResponse;
import com.patricia.notification.application.dto.response.NotificationResponse;
import com.patricia.notification.application.dto.response.UnreadNotificationCountResponse;
import com.patricia.notification.application.mapper.NotificationMapper;
import com.patricia.notification.domain.ports.in.GetNotificationsUseCase;
import com.patricia.notification.domain.ports.in.GetPreferencesUseCase;
import com.patricia.notification.domain.ports.in.GetUnreadCountUseCase;
import com.patricia.notification.domain.ports.in.MarkAsReadUseCase;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.ports.in.UpdatePreferencesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Notificaciones", description = "Gestión de notificaciones in-app del usuario")
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final SendNotificationUseCase sendNotificationUseCase;
    private final GetNotificationsUseCase getNotificationsUseCase;
    private final GetUnreadCountUseCase getUnreadCountUseCase;
    private final MarkAsReadUseCase markAsReadUseCase;
    private final GetPreferencesUseCase getPreferencesUseCase;
    private final UpdatePreferencesUseCase updatePreferencesUseCase;
    private final NotificationMapper mapper;

    @Operation(summary = "Crear notificación", description = "Crea y entrega una notificación al usuario. Si está conectado via WebSocket la recibe en tiempo real; si no, queda persistida en MongoDB")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notificación creada y entregada"),
            @ApiResponse(responseCode = "400", description = "Request inválido"),
            @ApiResponse(responseCode = "409", description = "Tipo de notificación deshabilitado por el usuario")
    })
    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(
            @Valid @RequestBody SendNotificationRequest request) {

        NotificationResponse response = mapper.toResponse(
                sendNotificationUseCase.execute(
                        request.getUserId(),
                        request.getType(),
                        request.getTitle(),
                        request.getBody(),
                        request.getReferenceId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar notificaciones", description = "Retorna notificaciones del usuario paginadas, ordenadas por fecha descendente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de notificaciones"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId,
            @Parameter(description = "Número de página (base 0)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "20") @RequestParam(defaultValue = "20") int size) {

        List<NotificationResponse> response = getNotificationsUseCase
                .execute(UUID.fromString(userId), page, size)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Contar no leídas", description = "Retorna el número de notificaciones no leídas del usuario")
    @ApiResponse(responseCode = "200", description = "Conteo de notificaciones no leídas")
    @GetMapping("/unread/count")
    public ResponseEntity<UnreadNotificationCountResponse> getUnreadCount(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId) {

        int count = getUnreadCountUseCase.execute(UUID.fromString(userId));
        return ResponseEntity.ok(mapper.toUnreadCountResponse(count));
    }

    @Operation(summary = "Marcar una como leída", description = "Marca una notificación específica como leída. Solo el propietario puede marcarla")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Marcada como leída"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Void> markSingleAsRead(
            @Parameter(description = "ID de la notificación", example = "550e8400-e29b-41d4-a716-446655440000") @PathVariable String notificationId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId) {

        markAsReadUseCase.executeSingle(UUID.fromString(notificationId), UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Marcar todas como leídas", description = "Marca todas las notificaciones del usuario como leídas")
    @ApiResponse(responseCode = "204", description = "Todas marcadas como leídas")
    @PutMapping("/read")
    public ResponseEntity<Void> markAllAsRead(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId) {

        markAsReadUseCase.executeAll(UUID.fromString(userId));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener preferencias", description = "Retorna las preferencias de notificación del usuario. Si no existen, retorna los valores por defecto")
    @ApiResponse(responseCode = "200", description = "Preferencias del usuario")
    @GetMapping("/preferences")
    public ResponseEntity<NotificationPreferencesResponse> getPreferences(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId) {

        return ResponseEntity.ok(
                mapper.toPreferencesResponse(
                        getPreferencesUseCase.execute(UUID.fromString(userId))));
    }

    @Operation(summary = "Actualizar preferencia", description = "Habilita o deshabilita un tipo de notificación específico para el usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preferencias actualizadas"),
            @ApiResponse(responseCode = "400", description = "Request inválido")
    })
    @PutMapping("/preferences")
    public ResponseEntity<NotificationPreferencesResponse> updatePreferences(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody UpdatePreferencesRequest request) {

        return ResponseEntity.ok(
                mapper.toPreferencesResponse(
                        updatePreferencesUseCase.execute(
                                UUID.fromString(userId),
                                request.getType(),
                                request.getEnabled())));
    }
}