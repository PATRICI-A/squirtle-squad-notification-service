package com.patricia.notification.infrastructure.adapters.persistence.repository;

import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationPreferencesDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoPreferencesRepository
        extends MongoRepository<NotificationPreferencesDocument, String> {

    Optional<NotificationPreferencesDocument> findByUserId(String userId);
}