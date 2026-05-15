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

/**
 * REST controller for registering event reminders.
 *
 * <p>Once registered, the scheduler automatically sends notifications to the user
 * 24 hours and 1 hour before the event date.</p>
 */
@Tag(name = "Event Reminders", description = "Register and manage event reminders")
@RestController
@RequestMapping("/api/event-reminders")
@RequiredArgsConstructor
public class EventReminderController {

    private final CreateEventReminderUseCase createEventReminderUseCase;

    /**
     * Registers a new event reminder for the specified user and event.
     */
    @Operation(
            summary = "Create event reminder",
            description = "Registers an event reminder. The scheduler will automatically send notifications 24 hours and 1 hour before the event date."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reminder created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request — date in the past or exceeds the 365-day limit")
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

    /**
     * Maps an {@link EventReminder} domain object to its API response representation.
     *
     * <p>Kept local to this controller because no other entry point needs this mapping.</p>
     */
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
