package com.patricia.notification.application.usecase;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.ports.in.GetNotificationsUseCase;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Retrieves a paginated list of notifications for a user, ordered by creation date descending.
 */
@Component
@RequiredArgsConstructor
public class GetNotificationsUseCaseImpl implements GetNotificationsUseCase {

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> execute(UUID userId, int page, int size) {
        return notificationRepository.findByUserIdPaged(userId, page, size);
    }
}
