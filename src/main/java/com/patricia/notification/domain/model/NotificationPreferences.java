package com.patricia.notification.domain.model;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.exceptions.NotificationTypeDisabledException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationPreferences {

    private String id;
    private String userId;
    private boolean connectionRequest;
    private boolean parcheMessage;
    private boolean eventReminder;
    private boolean nearbyParche;
    private boolean achievementUnlocked;
    private boolean parcheInvitation;
    private LocalDateTime updatedAt;

    public boolean isEnabled(NotificationType type) {
        return switch (type) {
            case CONNECTION_REQUEST -> this.connectionRequest;
            case PARCHE_MESSAGE -> this.parcheMessage;
            case EVENT_REMINDER -> this.eventReminder;
            case NEARBY_PARCHE -> this.nearbyParche;
            case ACHIEVEMENT_UNLOCKED -> this.achievementUnlocked;
            case PARCHE_INVITATION -> this.parcheInvitation;
        };
    }

    public void update(NotificationType type, boolean enabled) {
        switch (type) {
            case CONNECTION_REQUEST -> this.connectionRequest = enabled;
            case PARCHE_MESSAGE -> this.parcheMessage = enabled;
            case EVENT_REMINDER -> this.eventReminder = enabled;
            case NEARBY_PARCHE -> this.nearbyParche = enabled;
            case ACHIEVEMENT_UNLOCKED -> this.achievementUnlocked = enabled;
            case PARCHE_INVITATION -> this.parcheInvitation = enabled;
        };
        this.updatedAt = LocalDateTime.now();
    }
}