package com.patricia.notification.entrypoints.messaging.dto;

import jakarta.validation.constraints.NotBlank;
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
public class ConnectionRequestEventDto {
    @NotNull
    private UUID targetUserId;

    @NotBlank
    private String requesterUserName;

    @NotNull
    private UUID requesterId;
}
