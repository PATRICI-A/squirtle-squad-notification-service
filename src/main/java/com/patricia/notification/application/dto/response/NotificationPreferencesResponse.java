package com.patricia.notification.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationPreferencesResponse {
    private boolean connectionRequest;
    private boolean parcheMessage;
    private boolean eventReminder;
    private boolean nearbyParche;
    private boolean achievementUnlocked;
    private boolean parcheInvitation;
}