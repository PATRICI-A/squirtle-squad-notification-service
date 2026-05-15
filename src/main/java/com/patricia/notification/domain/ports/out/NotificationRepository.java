package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.Notification;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Output port for persisting and querying notifications.
 *
 * <p>Implemented by the MongoDB persistence adapter in the infrastructure layer.</p>
 */
public interface NotificationRepository {

    /**
     * Persists a new notification or updates an existing one.
     *
     * @param notification the notification to save
     * @return the saved notification with any generated fields populated
     */
    Notification save(Notification notification);

    /**
     * Finds a notification by its unique identifier.
     *
     * @param id the notification ID
     * @return an {@link Optional} containing the notification, or empty if not found
     */
    Optional<Notification> findById(UUID id);

    /**
     * Returns a page of notifications for the given user, ordered by creation date descending.
     *
     * @param userId the recipient user ID
     * @param page   zero-based page index
     * @param size   maximum number of results to return
     * @return list of notifications; empty if none match
     */
    List<Notification> findByUserIdPaged(UUID userId, int page, int size);

    /**
     * Returns the number of unread notifications for the given user.
     *
     * @param userId the recipient user ID
     * @return count of notifications where {@code read} is {@code false}
     */
    int countUnreadByUserId(UUID userId);

    /**
     * Sets {@code read = true} on all notifications belonging to the given user.
     *
     * @param userId the recipient user ID
     */
    void markAllAsReadByUserId(UUID userId);
}
