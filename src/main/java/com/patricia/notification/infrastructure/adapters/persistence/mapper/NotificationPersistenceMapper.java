package com.patricia.notification.infrastructure.adapters.persistence.mapper;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationDocument;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotificationPersistenceMapper {

    public NotificationDocument toDocument(Notification notification) {
        return NotificationDocument.builder()
                .id(notification.getId() != null
                        ? notification.getId().toString() : UUID.randomUUID().toString())
                .userId(notification.getUserId() != null
                        ? notification.getUserId().toString() : null)
                .recipientEmail(notification.getRecipientEmail())
                .type(notification.getType().name())
                .channel(notification.getChannel().name())
                .title(notification.getTitle())
                .body(notification.getBody())
                .read(notification.isRead())
                .referenceId(notification.getReferenceId() != null
                        ? notification.getReferenceId().toString() : null)
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public Notification toDomain(NotificationDocument doc) {
        return Notification.builder()
                .id(doc.getId() != null ? UUID.fromString(doc.getId()) : null)
                .userId(doc.getUserId() != null
                        ? UUID.fromString(doc.getUserId()) : null)
                .recipientEmail(doc.getRecipientEmail())
                .type(NotificationType.valueOf(doc.getType()))
                .channel(NotificationChannel.valueOf(doc.getChannel()))
                .title(doc.getTitle())
                .body(doc.getBody())
                .read(doc.isRead())
                .referenceId(doc.getReferenceId() != null
                        ? UUID.fromString(doc.getReferenceId()) : null)
                .createdAt(doc.getCreatedAt())
                .build();
    }
}