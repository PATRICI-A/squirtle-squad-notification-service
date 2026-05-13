package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.Notification;
import java.util.List;
import java.util.UUID;

public interface GetNotificationsUseCase {
    List<Notification> execute(UUID userId, int page, int size);
}