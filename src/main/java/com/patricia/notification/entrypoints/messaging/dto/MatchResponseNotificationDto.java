package com.patricia.notification.entrypoints.messaging.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MatchResponseNotificationDto {
    private UUID senderUserId;
    private UUID receiverUserId;
    private MatchStatus status;
    private LocalDateTime respondedAt;
}