package com.patricia.notification.domain.model;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.exceptions.NotificationTypeDisabledException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * User preferences that control which notification types are delivered.
 *
 * <p>Each boolean flag corresponds to a {@link NotificationType}. When a flag is
 * {@code false}, any attempt to send that notification type to the user results in
 * a {@link NotificationTypeDisabledException}. Default preferences are applied when
 * no record exists for a user.</p>
 */
@Getter
@Builder
public class NotificationPreferences {

    private UUID id;
    private UUID userId;
    private boolean connectionRequest;
    private boolean parcheMessage;
    private boolean eventReminder;
    private boolean nearbyParche;
    private boolean achievementUnlocked;
    private boolean otpVerification;
    private boolean passwordReset;
    private boolean invitationAccepted;
    private boolean invitationSent;
    private boolean memberJoined;
    private boolean matchReceived;
    private boolean matchResponse;
    private boolean friendshipCreated;
    private boolean chatMessage;

    /** Timestamp of the last preference change. */
    private LocalDateTime updatedAt;

    /**
     * Returns whether the given notification type is currently enabled for this user.
     *
     * @param type the notification type to check
     * @return {@code true} if the user has enabled notifications of this type
     */
    public boolean isEnabled(NotificationType type) {
        return switch (type) {
            case CONNECTION_REQUEST -> this.connectionRequest;
            case PARCHE_MESSAGE -> this.parcheMessage;
            case EVENT_REMINDER -> this.eventReminder;
            case NEARBY_PARCHE -> this.nearbyParche;
            case ACHIEVEMENT_UNLOCKED -> this.achievementUnlocked;
            case OTP_VERIFICATION -> this.otpVerification;
            case PASSWORD_RESET -> this.passwordReset;
            case INVITATION_ACCEPTED -> this.invitationAccepted;
            case INVITATION_SENT -> this.invitationSent;
            case MEMBER_JOINED -> this.memberJoined;
            case MATCH_RECEIVED -> this.matchReceived;
            case MATCH_RESPONSE -> this.matchResponse;
            case FRIENDSHIP_CREATED -> this.friendshipCreated;
            case CHAT_MESSAGE -> this.chatMessage;
        };
    }

    /**
     * Updates the enabled state for a specific notification type and records the
     * modification timestamp.
     *
     * @param type    the notification type to configure
     * @param enabled {@code true} to enable, {@code false} to disable
     */
    public void update(NotificationType type, boolean enabled) {
        switch (type) {
            case CONNECTION_REQUEST -> this.connectionRequest = enabled;
            case PARCHE_MESSAGE -> this.parcheMessage = enabled;
            case EVENT_REMINDER -> this.eventReminder = enabled;
            case NEARBY_PARCHE -> this.nearbyParche = enabled;
            case ACHIEVEMENT_UNLOCKED -> this.achievementUnlocked = enabled;
            case OTP_VERIFICATION -> this.otpVerification = enabled;
            case PASSWORD_RESET -> this.passwordReset = enabled;
            case INVITATION_ACCEPTED -> this.invitationAccepted = enabled;
            case INVITATION_SENT -> this.invitationSent = enabled;
            case MEMBER_JOINED -> this.memberJoined = enabled;
            case MATCH_RECEIVED -> this.matchReceived = enabled;
            case MATCH_RESPONSE -> this.matchResponse = enabled;
            case FRIENDSHIP_CREATED -> this.friendshipCreated = enabled;
            case CHAT_MESSAGE -> this.chatMessage = enabled;
        };
        this.updatedAt = LocalDateTime.now();
    }
}
