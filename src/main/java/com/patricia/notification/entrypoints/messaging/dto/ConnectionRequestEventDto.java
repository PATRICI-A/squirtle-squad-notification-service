package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

/**
 * Event DTO for connection request events published by the social service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionRequestEventDto {

    /** ID of the user who will receive the connection request notification. */
    @NotNull
    private UUID targetUserId;

    /** Display name of the user who sent the request. */
    @NotBlank
    private String requesterUserName;

    /** ID of the user who sent the request, used as {@code referenceId} in the notification. */
    @NotNull
    private UUID requesterId;
}
