package com.patricia.notification.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnreadNotificationCountResponse {
    private int count;
}