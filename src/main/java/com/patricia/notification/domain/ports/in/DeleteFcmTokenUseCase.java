package com.patricia.notification.domain.ports.in;

public interface DeleteFcmTokenUseCase {
    void execute(String userId, String device);
}