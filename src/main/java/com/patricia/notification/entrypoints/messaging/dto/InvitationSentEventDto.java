package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * Event DTO for invitation sent events published by the hangout service.
 *
 * <p>The invited student is notified when a captain sends them a private parche invitation.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationSentEventDto {

    @NotNull
    private UUID invitationId;

    @NotNull
    private UUID parcheId;

    /** ID of the student who will receive the invitation notification. */
    @NotNull
    private UUID invitedStudentId;

    /** ID of the captain who sent the invitation. */
    @NotNull
    private UUID capatinId;
}
