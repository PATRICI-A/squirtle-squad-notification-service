package com.patricia.notification.application.usecase;

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
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SendNotificationUseCaseImpl implements SendNotificationUseCase {

    private final NotificationRepository notificationRepository;
    private final PreferencesRepository preferencesRepository;
    private final List<NotificationDeliveryPort> deliveryPorts;

    @Override
    public Notification execute(UUID userId, NotificationType type,
                                String title, String body, UUID referenceId) {

        NotificationChannel channel = NotificationChannel.IN_APP;

        NotificationPreferences preferences = preferencesRepository
                .findByUserId(userId)
                .orElseGet(() -> buildDefaultPreferences(userId));

        if (!preferences.isEnabled(type)) {
            throw new NotificationTypeDisabledException(userId, type);
        }

        Notification notification = Notification.builder()
                .userId(userId)
                .type(type)
                .channel(channel)
                .title(title)
                .body(body)
                .read(false)
                .referenceId(referenceId)
                .createdAt(LocalDateTime.now())
                .build();

        Notification saved = notificationRepository.save(notification);

        deliveryPorts.stream()
                .filter(port -> port.supportedChannel() == channel)
                .findFirst()
                .ifPresent(port -> port.deliver(saved));

        return saved;
    }

    private NotificationPreferences buildDefaultPreferences(UUID userId) {
        return NotificationPreferences.builder()
                .userId(userId)
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .otpVerification(true)
                .passwordReset(true)
                .invitationAccepted(true)
                .invitationSent(true)
                .memberJoined(true)
                .build();
    }
}