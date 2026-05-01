package com.patricia.notification.infrastructure.adapters.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Document(collection = "notification_quotas")
@CompoundIndex(name = "userId_date", def = "{'userId': 1, 'date': 1}", unique = true)
public class NotificationQuotaDocument {

    @Id
    private String id;
    private String userId;
    private LocalDate date;
    private int pushCount;
}