package com.patricia.notification.infrastructure.adapters.persistence.repository;

import com.patricia.notification.infrastructure.adapters.persistence.entity.FcmTokenDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MongoFcmTokenRepository
        extends MongoRepository<FcmTokenDocument, String> {

    List<FcmTokenDocument> findAllByUserId(String userId);

    Optional<FcmTokenDocument> findByUserIdAndDevice(String userId, String device);

    void deleteByUserIdAndDevice(String userId, String device);
}