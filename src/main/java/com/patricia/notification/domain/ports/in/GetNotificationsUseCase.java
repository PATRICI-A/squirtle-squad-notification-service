package com.patricia.notification.domain.ports.in;

import com.patricia.notification.domain.model.Notification;
import java.util.List;

public interface GetNotificationsUseCase {
    List<Notification> execute(String userId, int page, int size);
}