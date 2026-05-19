package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Event DTO for parche group chat message events published by the parche service.
 *
 * <p>Triggered when a member posts a message in a parche chat and the target user
 * is not currently active in that chat.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParcheMessageEventDto {

    /** ID of the user who will receive the notification. */
    @NotNull
    private UUID targetUserId;

    /** Display name of the user who sent the message. */
    @NotBlank
    private String senderName;

    /** ID of the parche where the message was sent, used as referenceId. */
    @NotNull
    private UUID parcheId;

    /** Name of the parche, used in the notification body. */
    @NotBlank
    private String parcheName;
}
