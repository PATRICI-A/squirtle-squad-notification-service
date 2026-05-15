package com.patricia.notification.domain.ports.in;

import java.util.UUID;

/**
 * Input port for retrieving the number of unread notifications for a user.
 */
public interface GetUnreadCountUseCase {

    /**
     * Returns the count of unread notifications for the specified user.
     *
     * @param userId ID of the user
     * @return number of notifications where {@code read} is {@code false}
     */
    int execute(UUID userId);
}
