package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Event DTO for invitation accepted events published by the hangout service.
 *
 * <p>The captain is notified when a student accepts their invitation to a parche.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationAcceptedEventDto {

    @NotNull
    private UUID invitationId;

    @NotNull
    private UUID parcheId;

    /** ID of the student who accepted the invitation. */
    @NotNull
    private UUID studentId;

    /** ID of the parche captain who will receive the notification. */
    @NotNull
    private UUID captainId;

    @NotNull
    private LocalDateTime occurredAt;
}
