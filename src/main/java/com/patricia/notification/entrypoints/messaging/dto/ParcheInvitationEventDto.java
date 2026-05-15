package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * Event DTO for parche invitation events published by the parche service.
 *
 * <p>Triggered when a user is invited to join a parche by another user.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParcheInvitationEventDto {

    /** ID of the user who will receive the invitation notification. */
    @NotNull
    private UUID targetUserId;

    /** Display name of the user who sent the invitation. */
    @NotBlank
    private String inviterUserName;

    @NotNull
    private UUID parcheId;

    @NotBlank
    private String parcheName;
}
