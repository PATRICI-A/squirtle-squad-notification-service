package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationAcceptedEventDto {
    @NotNull
    private UUID invitationId;

    @NotNull
    private UUID parcheId;

    @NotNull
    private UUID studentId;

    @NotNull
    private UUID captainId;

    @NotNull
    private LocalDateTime ocurredAt;
}
