package com.patricia.notification.domain.model;

import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Notification {

    private String id;
    private String userId;
    private NotificationType type;
    private NotificationChannel channel;
    private String title;
    private String body;
    private boolean read;
    private String referenceId;
    private LocalDateTime createdAt;

    public void markAsRead() {
        this.read = true;
    }

    public boolean isInApp() {
        return this.channel == NotificationChannel.IN_APP;
    }
}