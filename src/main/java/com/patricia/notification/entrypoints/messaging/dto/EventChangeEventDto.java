package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Event DTO for event modification or cancellation events published by the events service.
 *
 * <p>Triggered when an event that a user RSVPed to is modified or cancelled.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventChangeEventDto {

    /** ID of the user who RSVPed and will receive the notification. */
    @NotNull
    private UUID targetUserId;

    /** ID of the event that was changed, used as referenceId. */
    @NotNull
    private UUID eventId;

    /** Name of the event. */
    @NotBlank
    private String eventName;

    /** Description of the change (e.g. "cancelado", "modificado"). */
    @NotBlank
    private String changeDescription;
}
