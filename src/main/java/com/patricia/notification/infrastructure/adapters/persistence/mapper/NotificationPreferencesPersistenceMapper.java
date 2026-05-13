package com.patricia.notification.infrastructure.adapters.persistence.mapper;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationPreferencesDocument;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationPreferencesPersistenceMapper {

    public NotificationPreferencesDocument toDocument(NotificationPreferences preferences) {
        return NotificationPreferencesDocument.builder()
                .id(preferences.getId() != null ? preferences.getId().toString() : null)
                .userId(preferences.getUserId() != null
                        ? preferences.getUserId().toString() : null)
                .connectionRequest(preferences.isConnectionRequest())
                .parcheMessage(preferences.isParcheMessage())
                .eventReminder(preferences.isEventReminder())
                .nearbyParche(preferences.isNearbyParche())
                .achievementUnlocked(preferences.isAchievementUnlocked())
                .parcheInvitation(preferences.isParcheInvitation())
                .otpVerification(preferences.isOtpVerification())
                .passwordReset(preferences.isPasswordReset())
                .invitationAccepted(preferences.isInvitationAccepted())
                .invitationSent(preferences.isInvitationSent())
                .memberJoined(preferences.isMemberJoined())
                .updatedAt(preferences.getUpdatedAt())
                .build();
    }

    public NotificationPreferences toDomain(NotificationPreferencesDocument doc) {
        return NotificationPreferences.builder()
                .id(doc.getId() != null ? UUID.fromString(doc.getId()) : null)
                .userId(doc.getUserId() != null
                        ? UUID.fromString(doc.getUserId()) : null)
                .connectionRequest(doc.isConnectionRequest())
                .parcheMessage(doc.isParcheMessage())
                .eventReminder(doc.isEventReminder())
                .nearbyParche(doc.isNearbyParche())
                .achievementUnlocked(doc.isAchievementUnlocked())
                .parcheInvitation(doc.isParcheInvitation())
                .otpVerification(doc.isOtpVerification())
                .passwordReset(doc.isPasswordReset())
                .invitationAccepted(doc.isInvitationAccepted())
                .invitationSent(doc.isInvitationSent())
                .memberJoined(doc.isMemberJoined())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }
}