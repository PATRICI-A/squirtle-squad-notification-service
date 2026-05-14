package com.patricia.notification.application.usecase;

import com.patricia.notification.domain.exceptions.NotificationNotFoundException;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.ports.in.MarkAsReadUseCase;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MarkAsReadUseCaseImpl implements MarkAsReadUseCase {

    private final NotificationRepository notificationRepository;

    @Override
    public void executeSingle(UUID notificationId, UUID userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));

        notification.markAsRead();
        notificationRepository.save(notification);
    }

    @Override
    public void executeAll(UUID userId) {
        notificationRepository.markAllAsReadByUserId(userId);
    }
}