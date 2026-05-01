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
@Document(collection = "fcm_tokens")
@CompoundIndex(name = "userId_device", def = "{'userId': 1, 'device': 1}", unique = true)
public class FcmTokenDocument {

    @Id
    private String id;
    private String userId;
    private String token;
    private String device;
    private LocalDateTime updatedAt;
}