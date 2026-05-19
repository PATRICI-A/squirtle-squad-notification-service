package com.patricia.notification.infrastructure.adapters.persistence.mapper;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationPreferencesDocument;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Maps between the {@link NotificationPreferences} domain model and the
 * {@link NotificationPreferencesDocument} MongoDB document.
 */
@Component
public class NotificationPreferencesPersistenceMapper {

    /**
     * Converts a domain {@link NotificationPreferences} to a {@link NotificationPreferencesDocument}.
     *
     * @param {preferences} the domain object to convert
     * @return the corresponding MongoDB document
     */
    private UUID safeParseUUID(String value) {
        if (value == null) return null;
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public NotificationPreferencesDocument toDocument(NotificationPreferences preferences) {
        return NotificationPreferencesDocument.builder()
                .id(preferences.getId() != null ? preferences.getId().toString() : UUID.randomUUID().toString())
                .userId(preferences.getUserId() != null
                        ? preferences.getUserId().toString() : null)
                .connectionRequest(preferences.isConnectionRequest())
                .parcheMessage(preferences.isParcheMessage())
                .eventReminder(preferences.isEventReminder())
                .nearbyParche(preferences.isNearbyParche())
                .achievementUnlocked(preferences.isAchievementUnlocked())
                .otpVerification(preferences.isOtpVerification())
                .passwordReset(preferences.isPasswordReset())
                .invitationAccepted(preferences.isInvitationAccepted())
                .invitationSent(preferences.isInvitationSent())
                .memberJoined(preferences.isMemberJoined())
                .matchReceived(preferences.isMatchReceived())
                .matchResponse(preferences.isMatchResponse())
                .friendshipCreated(preferences.isFriendshipCreated())
                .chatMessage(preferences.isChatMessage())
                .updatedAt(preferences.getUpdatedAt())
                .build();
    }

    /**
     * Converts a {@link NotificationPreferencesDocument} from MongoDB to its domain representation.
     *
     * @param doc the MongoDB document to convert
     * @return the corresponding domain {@link NotificationPreferences}
     */
    public NotificationPreferences toDomain(NotificationPreferencesDocument doc) {
        return NotificationPreferences.builder()
                .id(safeParseUUID(doc.getId()))
                .userId(doc.getUserId() != null
                        ? UUID.fromString(doc.getUserId()) : null)
                .connectionRequest(doc.isConnectionRequest())
                .parcheMessage(doc.isParcheMessage())
                .eventReminder(doc.isEventReminder())
                .nearbyParche(doc.isNearbyParche())
                .achievementUnlocked(doc.isAchievementUnlocked())
                .otpVerification(doc.isOtpVerification())
                .passwordReset(doc.isPasswordReset())
                .invitationAccepted(doc.isInvitationAccepted())
                .invitationSent(doc.isInvitationSent())
                .memberJoined(doc.isMemberJoined())
                .matchReceived(doc.isMatchReceived())
                .matchResponse(doc.isMatchResponse())
                .friendshipCreated(doc.isFriendshipCreated())
                .chatMessage(doc.isChatMessage())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }
}
