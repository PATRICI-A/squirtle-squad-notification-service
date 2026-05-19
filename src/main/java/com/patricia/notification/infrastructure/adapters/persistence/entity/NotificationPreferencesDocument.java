package com.patricia.notification.infrastructure.adapters.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MongoDB document representing a user's notification preferences.
 *
 * <p>{@code userId} has a unique index to enforce one preferences record per user.
 * The boolean fields mirror the {@link com.patricia.notification.domain.model.NotificationPreferences}
 * domain model one-to-one.</p>
 *
 * <p>Maps to the {@code notification_preferences} collection.</p>
 */
@Getter
@Setter
@Builder
@Document(collection = "notification_preferences")
public class NotificationPreferencesDocument {

    @Id
    private String id;

    /** Unique per user — enforced by MongoDB index. */
    @Indexed(unique = true)
    private String userId;

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
    private LocalDateTime updatedAt;
}
