package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.Notification;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findById(UUID id);
    List<Notification> findByUserIdPaged(UUID userId, int page, int size);
    int countUnreadByUserId(UUID userId);
    void markAllAsReadByUserId(UUID userId);
}