package com.patricia.notification.domain.ports.out;

import com.patricia.notification.domain.model.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findById(String id);
    List<Notification> findByUserIdPaged(String userId, int page, int size);
    int countUnreadByUserId(String userId);
    void markAllAsReadByUserId(String userId);
}