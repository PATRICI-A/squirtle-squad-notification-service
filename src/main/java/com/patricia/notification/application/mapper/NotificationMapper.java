package com.patricia.notification.application.mapper;

import com.patricia.notification.application.dto.response.NotificationPreferencesResponse;
import com.patricia.notification.application.dto.response.NotificationResponse;
import com.patricia.notification.application.dto.response.UnreadNotificationCountResponse;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.NotificationPreferences;
import org.springframework.stereotype.Component;

/**
 * Maps domain models to API response DTOs.
 *
 * <p>Keeps the domain layer decoupled from the transport representation by
 * translating domain objects into serializable response payloads.</p>
 */
@Component
public class NotificationMapper {

    /**
     * Converts a {@link Notification} domain object to its API response representation.
     *
     * @param notification the domain notification to convert
     * @return the corresponding {@link NotificationResponse}
     */
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

    /**
     * Converts a {@link NotificationPreferences} domain object to its API response representation.
     *
     * @param preferences the domain preferences to convert
     * @return the corresponding {@link NotificationPreferencesResponse}
     */
    public NotificationPreferencesResponse toPreferencesResponse(
            NotificationPreferences preferences) {
        return NotificationPreferencesResponse.builder()
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
                .eventChange(preferences.isEventChange())
                .build();
    }

    /**
     * Wraps a raw unread count integer into its API response representation.
     *
     * @param count the number of unread notifications
     * @return the corresponding {@link UnreadNotificationCountResponse}
     */
    public UnreadNotificationCountResponse toUnreadCountResponse(int count) {
        return UnreadNotificationCountResponse.builder()
                .count(count)
                .build();
    }
}
