package com.patricia.notification.application.usecase;

import com.patricia.notification.domain.exceptions.InvalidNotificationException;
import com.patricia.notification.domain.exceptions.NotificationTypeDisabledException;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.SendNotificationUseCase;
import com.patricia.notification.domain.ports.out.NotificationDeliveryPort;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import com.patricia.notification.domain.ports.out.PreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SendNotificationUseCaseImpl implements SendNotificationUseCase {

    private final NotificationRepository notificationRepository;
    private final PreferencesRepository preferencesRepository;
    private final NotificationDeliveryPort notificationDeliveryPort;

    @Override
    public Notification execute(String userId, NotificationType type,
                                NotificationChannel channel, String title,
                                String body, String referenceId) {

        if (userId == null || userId.isBlank()) {
            throw new InvalidNotificationException("el campo userId es obligatorio");
        }
        if (type == null) {
            throw new InvalidNotificationException("el campo type es obligatorio");
        }
        if (body == null || body.isBlank()) {
            throw new InvalidNotificationException("el campo body no puede estar vacío");
        }

        NotificationPreferences preferences = preferencesRepository
                .findByUserId(userId)
                .orElseGet(() -> buildDefaultPreferences(userId));

        if (!preferences.isEnabled(type)) {
            throw new NotificationTypeDisabledException(userId, type);
        }

        Notification notification = Notification.builder()
                .userId(userId)
                .type(type)
                .channel(NotificationChannel.IN_APP)
                .title(title)
                .body(body)
                .read(false)
                .referenceId(referenceId)
                .createdAt(LocalDateTime.now())
                .build();

        Notification saved = notificationRepository.save(notification);

        notificationDeliveryPort.deliver(saved);

        return saved;
    }

    private NotificationPreferences buildDefaultPreferences(String userId) {
        return NotificationPreferences.builder()
                .userId(userId)
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .build();
    }
}