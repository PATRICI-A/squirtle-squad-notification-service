package com.patricia.notification.domain.model;

import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Core domain entity representing a notification delivered to a user.
 *
 * <p>A notification is created when a domain event occurs (e.g. connection request,
 * OTP verification) and persisted regardless of whether the user is currently online.
 * Real-time delivery is attempted via WebSocket; unread notifications remain available
 * for later retrieval.</p>
 */
@Getter
@Builder
public class Notification {

    /** Unique identifier assigned at persistence time. */
    private UUID id;

    /** ID of the user who receives this notification. */
    private UUID userId;

    /** Email address used when the delivery channel is EMAIL. */
    private String recipientEmail;

    /** Category that determines how the notification is displayed and filtered. */
    private NotificationType type;

    /** Transport mechanism used to deliver this notification. */
    private NotificationChannel channel;

    /** Short summary shown in notification lists and push banners. Max 80 characters. */
    private String title;

    /** Full notification message. Max 200 characters. */
    private String body;

    /** Whether the recipient has viewed this notification. Defaults to false on creation. */
    private boolean read;

    /** ID of the entity that triggered this notification (e.g. parche, event, invitation). */
    private UUID referenceId;

    /** Timestamp when the notification was created, in ISO 8601 format. */
    private LocalDateTime createdAt;

    /** Whether this notification has been archived (older than 30 days). */
    private boolean archived;

    /** Timestamp when the notification was archived. */
    private LocalDateTime archivedAt;

    /**
     * Marks this notification as read by the recipient.
     */
    public void markAsRead() {
        this.read = true;
    }

    /**
     * Archives this notification, hiding it from normal queries.
     */
    public void archive() {
        this.archived = true;
        this.archivedAt = LocalDateTime.now();
    }

    /**
     * Returns {@code true} if this notification was delivered through the in-app channel.
     */
    public boolean isInApp() {
        return this.channel == NotificationChannel.IN_APP;
    }
}
