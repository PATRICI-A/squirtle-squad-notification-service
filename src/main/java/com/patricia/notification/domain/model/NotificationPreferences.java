package com.patricia.notification.domain.model;

import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.exceptions.NotificationTypeDisabledException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private boolean parcheInvitation;
    private boolean otpVerification;
    private boolean passwordReset;
    private boolean invitationAccepted;
    private boolean invitationSent;
    private boolean memberJoined;
    private LocalDateTime updatedAt;

    public boolean isEnabled(NotificationType type) {
        return switch (type) {
            case CONNECTION_REQUEST -> this.connectionRequest;
            case PARCHE_MESSAGE -> this.parcheMessage;
            case EVENT_REMINDER -> this.eventReminder;
            case NEARBY_PARCHE -> this.nearbyParche;
            case ACHIEVEMENT_UNLOCKED -> this.achievementUnlocked;
            case PARCHE_INVITATION -> this.parcheInvitation;
            case OTP_VERIFICATION -> this.otpVerification;
            case PASSWORD_RESET -> this.passwordReset;
            case INVITATION_ACCEPTED -> this.invitationAccepted;
            case INVITATION_SENT -> this.invitationSent;
            case MEMBER_JOINED -> this.memberJoined;
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
            case OTP_VERIFICATION -> this.otpVerification = enabled;
            case PASSWORD_RESET -> this.passwordReset = enabled;
            case INVITATION_ACCEPTED -> this.invitationAccepted = enabled;
            case INVITATION_SENT -> this.invitationSent = enabled;
            case MEMBER_JOINED -> this.memberJoined = enabled;
        };
        this.updatedAt = LocalDateTime.now();
    }
}