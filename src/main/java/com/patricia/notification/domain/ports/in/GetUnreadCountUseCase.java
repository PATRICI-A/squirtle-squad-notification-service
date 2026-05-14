package com.patricia.notification.domain.ports.in;

import java.util.UUID;

public interface GetUnreadCountUseCase {
    int execute(UUID userId);
}