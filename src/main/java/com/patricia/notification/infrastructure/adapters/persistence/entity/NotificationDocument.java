package com.patricia.notification.infrastructure.adapters.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(collection = "notifications")
@CompoundIndex(name = "userId_createdAt", def = "{'userId': 1, 'createdAt': -1}")
public class NotificationDocument {

    @Id
    private String id;
    private String userId;
    private String type;
    private String channel;
    private String title;
    private String body;
    private boolean read;
    private String referenceId;
    private LocalDateTime createdAt;
}