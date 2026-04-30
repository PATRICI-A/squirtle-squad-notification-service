package com.patricia.notification.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FcmToken {

    private String id;
    private String userId;
    private String token;
    private String device;
    private LocalDateTime updatedAt;

    public void refresh(String newToken) {
        this.token = newToken;
        this.updatedAt = LocalDateTime.now();
    }
}