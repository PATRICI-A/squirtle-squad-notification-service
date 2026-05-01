package com.patricia.notification.infrastructure.adapters.persistence.mapper;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationPreferencesDocument;
import org.springframework.stereotype.Component;

@Component
public class NotificationPreferencesPersistenceMapper {

    public NotificationPreferencesDocument toDocument(NotificationPreferences preferences) {
        return NotificationPreferencesDocument.builder()
                .id(preferences.getId())
                .userId(preferences.getUserId())
                .connectionRequest(preferences.isConnectionRequest())
                .parcheMessage(preferences.isParcheMessage())
                .eventReminder(preferences.isEventReminder())
                .nearbyParche(preferences.isNearbyParche())
                .achievementUnlocked(preferences.isAchievementUnlocked())
                .parcheInvitation(preferences.isParcheInvitation())
                .updatedAt(preferences.getUpdatedAt())
                .build();
    }

    public NotificationPreferences toDomain(NotificationPreferencesDocument doc) {
        return NotificationPreferences.builder()
                .id(doc.getId())
                .userId(doc.getUserId())
                .connectionRequest(doc.isConnectionRequest())
                .parcheMessage(doc.isParcheMessage())
                .eventReminder(doc.isEventReminder())
                .nearbyParche(doc.isNearbyParche())
                .achievementUnlocked(doc.isAchievementUnlocked())
                .parcheInvitation(doc.isParcheInvitation())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }
}