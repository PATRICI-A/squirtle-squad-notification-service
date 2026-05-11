package com.patricia.notification.entrypoints.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitationAcceptedEventDto {
    private UUID invitationId;
    private UUID parcheId;
    private UUID studentId;
    private UUID captainId;
    private LocalDateTime ocurredAt;
}
