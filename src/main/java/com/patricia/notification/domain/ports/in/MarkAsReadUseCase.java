package com.patricia.notification.domain.ports.in;

import java.util.UUID;

/**
 * Input port for marking notifications as read.
 */
public interface MarkAsReadUseCase {

    /**
     * Marks a single notification as read.
     *
     * @param notificationId ID of the notification to mark
     * @param userId         ID of the requesting user (used to verify ownership)
     * @throws com.patricia.notification.domain.exceptions.NotificationNotFoundException
     *         if no notification with the given ID exists
     */
    void executeSingle(UUID notificationId, UUID userId);

    /**
     * Marks all notifications belonging to the specified user as read.
     *
     * @param userId ID of the user whose notifications to mark
     */
    void executeAll(UUID userId);
}
