package com.patricia.notification.infrastructure.adapters.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "notification_preferences")
public class NotificationPreferencesDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

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
}