package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Event DTO for member joined events published by the hangout service.
 *
 * <p>The parche captain is notified when a new student joins their parche.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinedEventDto {

    /** ID of the parche captain who will receive the notification. */
    @NotNull
    private UUID capitanId;

    /** ID of the student who joined the parche. */
    @NotNull
    private UUID estudianteId;

    @NotNull
    private UUID nombreParche;

    @NotNull
    private LocalDateTime timestamp;
}
