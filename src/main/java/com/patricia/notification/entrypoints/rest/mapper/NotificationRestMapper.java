package com.patricia.notification.entrypoints.rest.mapper;

import com.patricia.notification.application.dto.response.NotificationPreferencesResponse;
import com.patricia.notification.application.dto.response.NotificationResponse;
import com.patricia.notification.application.dto.response.UnreadNotificationCountResponse;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.NotificationPreferences;
import org.springframework.stereotype.Component;

@Component
public class NotificationRestMapper {

    public NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .channel(notification.getChannel())
                .title(notification.getTitle())
                .body(notification.getBody())
                .read(notification.isRead())
                .referenceId(notification.getReferenceId())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public NotificationPreferencesResponse toPreferencesResponse(
            NotificationPreferences preferences) {
        return NotificationPreferencesResponse.builder()
                .connectionRequest(preferences.isConnectionRequest())
                .parcheMessage(preferences.isParcheMessage())
                .eventReminder(preferences.isEventReminder())
                .nearbyParche(preferences.isNearbyParche())
                .achievementUnlocked(preferences.isAchievementUnlocked())
                .parcheInvitation(preferences.isParcheInvitation())
                .build();
    }

    public UnreadNotificationCountResponse toUnreadCountResponse(int count) {
        return UnreadNotificationCountResponse.builder()
                .count(count)
                .build();
    }
}