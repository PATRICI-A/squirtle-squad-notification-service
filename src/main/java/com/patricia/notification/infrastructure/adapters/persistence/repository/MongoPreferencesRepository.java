package com.patricia.notification.infrastructure.adapters.persistence.repository;

import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationPreferencesDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for {@link NotificationPreferencesDocument}.
 *
 * <p>The {@code userId} field has a unique index on the collection, so at most one
 * preferences document exists per user.</p>
 */
public interface MongoPreferencesRepository
        extends MongoRepository<NotificationPreferencesDocument, String> {

    /**
     * Finds the preferences document for the given user.
     *
     * @param userId the user ID (string form)
     * @return an {@link Optional} containing the document, or empty if none exists
     */
    Optional<NotificationPreferencesDocument> findByUserId(String userId);
}
