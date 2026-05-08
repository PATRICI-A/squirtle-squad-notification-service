package com.patricia.notification.infrastructure.adapters.persistence.mapper;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationDocument;
import org.springframework.stereotype.Component;

@Component
public class NotificationPersistenceMapper {

    public NotificationDocument toDocument(Notification notification) {
        return NotificationDocument.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType().name())
                .channel(notification.getChannel().name())
                .title(notification.getTitle())
                .body(notification.getBody())
                .read(notification.isRead())
                .referenceId(notification.getReferenceId())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public Notification toDomain(NotificationDocument doc) {
        return Notification.builder()
                .id(doc.getId())
                .userId(doc.getUserId())
                .type(NotificationType.valueOf(doc.getType()))
                .channel(NotificationChannel.valueOf(doc.getChannel()))
                .title(doc.getTitle())
                .body(doc.getBody())
                .read(doc.isRead())
                .referenceId(doc.getReferenceId())
                .createdAt(doc.getCreatedAt())
                .build();
    }
}