package com.patricia.notification.entrypoints.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParcheInvitationEventDto {
    private String targetUserId;
    private String inviterUserName;
    private String parcheId;
    private String parcheName;
}