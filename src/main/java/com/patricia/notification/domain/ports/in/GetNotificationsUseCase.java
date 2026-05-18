package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.Notification;
import java.util.List;
import java.util.UUID;

/**
 * Input port for retrieving a paginated list of notifications for a user.
 *
 * <p>Results are sorted by creation date in descending order (most recent first).</p>
 */
public interface GetNotificationsUseCase {

    /**
     * Returns a page of notifications for the specified user.
     *
     * @param userId ID of the user whose notifications to retrieve
     * @param page   zero-based page index
     * @param size   number of notifications per page (max 100)
     * @return ordered list of notifications; empty list if none found
     */
    List<Notification> execute(UUID userId, int page, int size);
}
