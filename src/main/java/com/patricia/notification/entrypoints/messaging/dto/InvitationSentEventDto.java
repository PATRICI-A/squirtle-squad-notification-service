package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationSentEventDto {
    @NotNull
    private UUID invitationId;

    @NotNull
    private UUID parcheId;

    @NotNull
    private UUID invitedStudentId;

    @NotNull
    private UUID capatinId;
}
