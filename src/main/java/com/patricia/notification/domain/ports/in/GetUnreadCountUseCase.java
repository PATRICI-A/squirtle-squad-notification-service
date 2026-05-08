package com.patricia.notification.domain.ports.in;

public interface GetUnreadCountUseCase {
    int execute(String userId);
}