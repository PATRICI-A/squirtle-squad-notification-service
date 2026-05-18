package com.patricia.notification.infrastructure.adapters.persistence.repository;

import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

/**
 * Spring Data MongoDB repository for {@link NotificationDocument}.
 *
 * <p>Queries rely on the compound index {@code (userId, createdAt DESC)} defined on
 * the document class for efficient paginated lookups.</p>
 */
public interface MongoNotificationRepository
        extends MongoRepository<NotificationDocument, String> {

    /**
     * Returns notifications for the given user ordered by creation date descending.
     *
     * @param userId   the recipient user ID (string form)
     * @param pageable pagination parameters
     * @return ordered page of notification documents
     */
    List<NotificationDocument> findByUserIdOrderByCreatedAtDesc(
            String userId, Pageable pageable);

    /**
     * Counts notifications where {@code read = false} for the given user.
     *
     * @param userId the recipient user ID (string form)
     * @return number of unread notifications
     */
    int countByUserIdAndReadFalse(String userId);

    /**
     * Sets {@code read = true} on all notifications belonging to the given user
     * using a MongoDB {@code $set} update to avoid loading documents into memory.
     *
     * @param userId the recipient user ID (string form)
     */
    @Query("{'userId': ?0}")
    @Update("{'$set': {'read': true}}")
    void markAllAsReadByUserId(String userId);
}
