package com.patricia.notification.entrypoints.messaging.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class MatchReceivedNotificationDto {
    private UUID senderUserId;
    private UUID receiverUserId;
    private Double affinityPercentage;
    private LocalDateTime createdAt;
    private List<String> actions;
}