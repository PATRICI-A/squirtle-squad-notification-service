package com.patricia.notification.infrastructure.adapters.persistence.mapper;

import com.patricia.notification.domain.model.FcmToken;
import com.patricia.notification.infrastructure.adapters.persistence.entity.FcmTokenDocument;
import org.springframework.stereotype.Component;

@Component
public class FcmTokenPersistenceMapper {

    public FcmTokenDocument toDocument(FcmToken fcmToken) {
        return FcmTokenDocument.builder()
                .id(fcmToken.getId())
                .userId(fcmToken.getUserId())
                .token(fcmToken.getToken())
                .device(fcmToken.getDevice())
                .updatedAt(fcmToken.getUpdatedAt())
                .build();
    }

    public FcmToken toDomain(FcmTokenDocument doc) {
        return FcmToken.builder()
                .id(doc.getId())
                .userId(doc.getUserId())
                .token(doc.getToken())
                .device(doc.getDevice())
                .updatedAt(doc.getUpdatedAt())
                .build();
    }
}