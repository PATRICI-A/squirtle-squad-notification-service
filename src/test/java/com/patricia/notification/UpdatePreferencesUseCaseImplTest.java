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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdatePreferencesUseCaseImplTest {

    private static final UUID USER_ID     = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_NEW = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID USER_ID_3   = UUID.fromString("00000000-0000-0000-0000-000000000003");

    @Mock
    private PreferencesRepository preferencesRepository;

    @InjectMocks
    private UpdatePreferencesUseCaseImpl useCase;

    @Test
    @DisplayName("Debe actualizar una preferencia existente")
    void execute_shouldUpdateExistingPreference() {
        NotificationPreferences existing = NotificationPreferences.builder()
                .userId(USER_ID)
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .build();

        NotificationPreferences updated = NotificationPreferences.builder()
                .userId(USER_ID)
                .connectionRequest(true)
                .parcheMessage(false)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .build();

        when(preferencesRepository.findByUserId(USER_ID)).thenReturn(Optional.of(existing));
        when(preferencesRepository.save(any())).thenReturn(updated);

        NotificationPreferences result = useCase.execute(
                USER_ID, NotificationType.PARCHE_MESSAGE, false);

        assertThat(result.isParcheMessage()).isFalse();
        verify(preferencesRepository).save(any(NotificationPreferences.class));
    }

    @Test
    @DisplayName("Debe crear preferencias por defecto si no existen")
    void execute_shouldCreateDefaultPreferences_whenNotFound() {
        when(preferencesRepository.findByUserId(USER_ID_NEW)).thenReturn(Optional.empty());

        NotificationPreferences newPrefs = NotificationPreferences.builder()
                .userId(USER_ID_NEW)
                .connectionRequest(true)
                .parcheMessage(false)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .build();

        when(preferencesRepository.save(any())).thenReturn(newPrefs);

        NotificationPreferences result = useCase.execute(
                USER_ID_NEW, NotificationType.PARCHE_MESSAGE, false);

        assertThat(result.getUserId()).isEqualTo(USER_ID_NEW);
        assertThat(result.isParcheMessage()).isFalse();
        verify(preferencesRepository).save(any(NotificationPreferences.class));
    }

    @Test
    @DisplayName("Debe guardar las preferencias después de actualizar")
    void execute_shouldPersistUpdatedPreferences() {
        NotificationPreferences existing = NotificationPreferences.builder()
                .userId(USER_ID_3)
                .connectionRequest(false)
                .parcheMessage(true)
                .eventReminder(false)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .build();

        ArgumentCaptor<NotificationPreferences> captor = ArgumentCaptor.forClass(NotificationPreferences.class);

        when(preferencesRepository.findByUserId(USER_ID_3)).thenReturn(Optional.of(existing));
        when(preferencesRepository.save(captor.capture())).thenReturn(existing);

        useCase.execute(USER_ID_3, NotificationType.CONNECTION_REQUEST, true);

        verify(preferencesRepository).save(captor.getValue());
        assertThat(captor.getValue().isConnectionRequest()).isTrue();
    }
}
