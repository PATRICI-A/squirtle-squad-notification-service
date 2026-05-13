package com.patricia.notification.application.usecase;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.in.UpdatePreferencesUseCase;
import com.patricia.notification.domain.ports.out.PreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdatePreferencesUseCaseImpl implements UpdatePreferencesUseCase {

    private final PreferencesRepository preferencesRepository;

    @Override
    public NotificationPreferences execute(UUID userId,
                                           NotificationType type, boolean enabled) {

        NotificationPreferences preferences = preferencesRepository
                .findByUserId(userId)
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
                        .updatedAt(LocalDateTime.now())
                        .build());

        preferences.update(type, enabled);
        return preferencesRepository.save(preferences);
    }
}