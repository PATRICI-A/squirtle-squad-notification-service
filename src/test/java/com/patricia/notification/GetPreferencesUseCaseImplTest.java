package com.patricia.notification;

import com.patricia.notification.application.usecase.GetPreferencesUseCaseImpl;
import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.ports.out.PreferencesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPreferencesUseCaseImplTest {

    private static final UUID USER_ID     = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_NEW = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Mock
    private PreferencesRepository preferencesRepository;

    @InjectMocks
    private GetPreferencesUseCaseImpl useCase;

    @Test
    @DisplayName("Debe retornar las preferencias del repositorio si existen")
    void execute_shouldReturnPreferencesFromRepository() {
        NotificationPreferences prefs = NotificationPreferences.builder()
                .userId(USER_ID)
                .connectionRequest(true)
                .parcheMessage(false)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .build();

        when(preferencesRepository.findByUserId(USER_ID)).thenReturn(Optional.of(prefs));

        NotificationPreferences result = useCase.execute(USER_ID);

        assertThat(result).isEqualTo(prefs);
        assertThat(result.isParcheMessage()).isFalse();
    }

    @Test
    @DisplayName("Debe retornar preferencias por defecto si no existen en el repositorio")
    void execute_shouldReturnDefaultPreferences_whenNotFound() {
        when(preferencesRepository.findByUserId(USER_ID_NEW)).thenReturn(Optional.empty());

        NotificationPreferences result = useCase.execute(USER_ID_NEW);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(USER_ID_NEW);
        assertThat(result.isConnectionRequest()).isTrue();
        assertThat(result.isParcheMessage()).isTrue();
        assertThat(result.isEventReminder()).isTrue();
        assertThat(result.isNearbyParche()).isFalse();
        assertThat(result.isAchievementUnlocked()).isTrue();
    }
}
