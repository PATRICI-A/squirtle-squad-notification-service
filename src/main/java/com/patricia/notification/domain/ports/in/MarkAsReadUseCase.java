package com.patricia.notification.domain.ports.in;

public interface MarkAsReadUseCase {
    void executeSingle(String notificationId, String userId);
    void executeAll(String userId);
}