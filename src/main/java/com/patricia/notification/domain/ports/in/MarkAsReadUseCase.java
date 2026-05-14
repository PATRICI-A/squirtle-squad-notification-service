package com.patricia.notification.domain.ports.in;

import java.util.UUID;

public interface MarkAsReadUseCase {
    void executeSingle(UUID notificationId, UUID userId);
    void executeAll(UUID userId);
}