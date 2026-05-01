package com.patricia.notification.infrastructure.adapters.persistence.mapper;

import com.patricia.notification.domain.model.NotificationQuota;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationQuotaDocument;
import org.springframework.stereotype.Component;

@Component
public class NotificationQuotaPersistenceMapper {

    public NotificationQuotaDocument toDocument(NotificationQuota quota) {
        return NotificationQuotaDocument.builder()
                .id(quota.getId())
                .userId(quota.getUserId())
                .date(quota.getDate())
                .pushCount(quota.getPushCount())
                .build();
    }

    public NotificationQuota toDomain(NotificationQuotaDocument doc) {
        return NotificationQuota.builder()
                .id(doc.getId())
                .userId(doc.getUserId())
                .date(doc.getDate())
                .pushCount(doc.getPushCount())
                .build();
    }
}