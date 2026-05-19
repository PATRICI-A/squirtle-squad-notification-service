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
import java.util.List;
import java.util.UUID;

/**
 * Orchestrates notification creation and delivery.
 *
 * <p>Execution flow:
 * <ol>
 *   <li>Resolves the user's preferences (uses defaults when no record exists).</li>
 *   <li>Rejects the request if the notification type is disabled for that user.</li>
 *   <li>Persists the notification.</li>
 *   <li>Delegates delivery to the first {@link NotificationDeliveryPort} whose channel
 *       matches the resolved channel ({@code IN_APP}).</li>
 * </ol>
 * </p>
 */
@Component
@RequiredArgsConstructor
public class SendNotificationUseCaseImpl implements SendNotificationUseCase {

    private final NotificationRepository notificationRepository;
    private final PreferencesRepository preferencesRepository;
    private final List<NotificationDeliveryPort> deliveryPorts;

    @Override
    public Notification execute(UUID userId, NotificationType type,
                                String title, String body, UUID referenceId) {

        if (userId == null) {
            throw new InvalidNotificationException("userId no puede ser nulo");
        }
        if (type == null) {
            throw new InvalidNotificationException("type no puede ser nulo");
        }
        if (body == null || body.isBlank()) {
            throw new InvalidNotificationException("body no puede estar vacío");
        }

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

    /**
     * Builds the default preferences applied when a user has no saved configuration.
     * All types are enabled except {@code NEARBY_PARCHE}, which is opt-in.
     */
    private NotificationPreferences buildDefaultPreferences(UUID userId) {
        return NotificationPreferences.builder()
                .userId(userId)
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .otpVerification(true)
                .passwordReset(true)
                .invitationAccepted(true)
                .invitationSent(true)
                .memberJoined(true)
                .matchReceived(true)
                .matchResponse(true)
                .friendshipCreated(true)
                .build();
    }
}
