package com.patricia.notification.infrastructure.adapters.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * MongoDB document representing a persisted notification.
 *
 * <p>UUIDs ({@code userId}, {@code referenceId}) are stored as strings because
 * MongoDB does not have a native UUID type. The compound index on
 * {@code (userId, createdAt DESC)} supports the paginated notification list query.</p>
 *
 * <p>Maps to the {@code notifications} collection.</p>
 */
@Getter
@Setter
@Builder
@Document(collection = "notifications")
@CompoundIndex(name = "userId_createdAt", def = "{'userId': 1, 'createdAt': -1}")
public class NotificationDocument {

    @Id
    private String id;
    private String userId;
    private String recipientEmail;

    /** Stored as the enum name string (e.g. {@code "PARCHE_MESSAGE"}). */
    private String type;

    /** Stored as the enum name string (e.g. {@code "IN_APP"}). */
    private String channel;

    private String title;
    private String body;
    private boolean read;
    private String referenceId;
    private LocalDateTime createdAt;
    private boolean archived;
    private LocalDateTime archivedAt;
}
