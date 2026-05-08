package com.patricia.notification.infrastructure.adapters.persistence.repository;

import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface MongoNotificationRepository
        extends MongoRepository<NotificationDocument, String> {

    List<NotificationDocument> findByUserIdOrderByCreatedAtDesc(
            String userId, Pageable pageable);

    int countByUserIdAndReadFalse(String userId);

    @Query("{'userId': ?0}")
    @Update("{'$set': {'read': true}}")
    void markAllAsReadByUserId(String userId);
}