package com.patricia.notification.entrypoints.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParcheInvitationEventDto {
    private UUID targetUserId;
    private String inviterUserName;
    private UUID parcheId;
    private String parcheName;
}