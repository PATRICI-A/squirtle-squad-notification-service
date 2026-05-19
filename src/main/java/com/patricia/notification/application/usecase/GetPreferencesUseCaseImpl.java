package com.patricia.notification.application.usecase;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.ports.in.GetPreferencesUseCase;
import com.patricia.notification.domain.ports.out.PreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Retrieves a user's notification preferences, returning defaults when no record exists.
 *
 * <p>Default preferences: all types enabled except {@code NEARBY_PARCHE} (opt-in).
 * Default preferences are not persisted — they are only returned in-memory.</p>
 */
@Component
@RequiredArgsConstructor
public class GetPreferencesUseCaseImpl implements GetPreferencesUseCase {

    private final PreferencesRepository preferencesRepository;

    @Override
    public NotificationPreferences execute(UUID userId) {
        return preferencesRepository.findByUserId(userId)
                .orElseGet(() -> NotificationPreferences.builder()
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
                        .matchReceived(true)
                        .matchResponse(true)
                        .friendshipCreated(true)
                        .updatedAt(LocalDateTime.now())
                        .build());
    }
}
