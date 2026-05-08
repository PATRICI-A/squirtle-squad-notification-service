package com.patricia.notification;

import com.patricia.notification.application.usecase.UpdatePreferencesUseCaseImpl;
import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.out.PreferencesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdatePreferencesUseCaseImplTest {

    @Mock
    private PreferencesRepository preferencesRepository;

    @InjectMocks
    private UpdatePreferencesUseCaseImpl useCase;

    @Test
    @DisplayName("Debe actualizar una preferencia existente")
    void execute_shouldUpdateExistingPreference() {
        NotificationPreferences existing = NotificationPreferences.builder()
                .userId("user-123")
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .build();

        NotificationPreferences updated = NotificationPreferences.builder()
                .userId("user-123")
                .connectionRequest(true)
                .parcheMessage(false)  // cambió
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .build();

        when(preferencesRepository.findByUserId("user-123")).thenReturn(Optional.of(existing));
        when(preferencesRepository.save(any())).thenReturn(updated);

        NotificationPreferences result = useCase.execute(
                "user-123", NotificationType.PARCHE_MESSAGE, false);

        assertThat(result.isParcheMessage()).isFalse();
        verify(preferencesRepository).save(any(NotificationPreferences.class));
    }

    @Test
    @DisplayName("Debe crear preferencias por defecto si no existen")
    void execute_shouldCreateDefaultPreferences_whenNotFound() {
        when(preferencesRepository.findByUserId("user-nuevo")).thenReturn(Optional.empty());

        NotificationPreferences newPrefs = NotificationPreferences.builder()
                .userId("user-nuevo")
                .connectionRequest(true)
                .parcheMessage(false)  // actualizado a false
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .build();

        when(preferencesRepository.save(any())).thenReturn(newPrefs);

        NotificationPreferences result = useCase.execute(
                "user-nuevo", NotificationType.PARCHE_MESSAGE, false);

        assertThat(result.getUserId()).isEqualTo("user-nuevo");
        assertThat(result.isParcheMessage()).isFalse();
        verify(preferencesRepository).save(any(NotificationPreferences.class));
    }

    @Test
    @DisplayName("Debe guardar las preferencias después de actualizar")
    void execute_shouldPersistUpdatedPreferences() {
        NotificationPreferences existing = NotificationPreferences.builder()
                .userId("user-456")
                .connectionRequest(false)
                .parcheMessage(true)
                .eventReminder(false)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .build();

        ArgumentCaptor<NotificationPreferences> captor = ArgumentCaptor.forClass(NotificationPreferences.class);

        when(preferencesRepository.findByUserId("user-456")).thenReturn(Optional.of(existing));
        when(preferencesRepository.save(captor.capture())).thenReturn(existing);

        useCase.execute("user-456", NotificationType.CONNECTION_REQUEST, true);

        verify(preferencesRepository).save(captor.getValue());
        assertThat(captor.getValue().isConnectionRequest()).isTrue();
    }
}

