package com.patricia.notification.entrypoints.rest.controller;

import com.patricia.notification.application.dto.request.CreateEventReminderRequest;
import com.patricia.notification.application.dto.response.EventReminderResponse;
import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.domain.ports.in.CreateEventReminderUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Recordatorios", description = "Gestión de recordatorios de eventos")
@RestController
@RequestMapping("/api/event-reminders")
@RequiredArgsConstructor
public class EventReminderController {

    private final CreateEventReminderUseCase createEventReminderUseCase;

    @Operation(summary = "Crear recordatorio", description = "Registra un recordatorio de evento. El scheduler enviará notificaciones automáticas a las 24h y 1h antes del evento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recordatorio creado"),
            @ApiResponse(responseCode = "400", description = "Request inválido o fecha en el pasado")
    })
    @PostMapping
    public ResponseEntity<EventReminderResponse> createReminder(
            @Valid @RequestBody CreateEventReminderRequest request) {

        EventReminder reminder = createEventReminderUseCase.execute(
                request.getUserId(),
                request.getEventId(),
                request.getEventDate());

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(reminder));
    }

    private EventReminderResponse toResponse(EventReminder reminder) {
        return EventReminderResponse.builder()
                .id(reminder.getId())
                .userId(reminder.getUserId())
                .eventId(reminder.getEventId())
                .eventDate(reminder.getEventDate())
                .reminded24h(reminder.isReminded24h())
                .reminded1h(reminder.isReminded1h())
                .build();
    }
}
