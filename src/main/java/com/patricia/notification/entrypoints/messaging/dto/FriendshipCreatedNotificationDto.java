package com.patricia.notification.entrypoints.messaging.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FriendshipCreatedNotificationDto {
    private UUID userId1;
    private UUID userId2;
    private String relationType;
    private LocalDateTime createdAt;
}