package com.patricia.notification.infrastructure.adapters.persistence.repository;

import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationQuotaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MongoQuotaRepository
        extends MongoRepository<NotificationQuotaDocument, String> {

    Optional<NotificationQuotaDocument> findByUserIdAndDate(
            String userId, LocalDate date);
}