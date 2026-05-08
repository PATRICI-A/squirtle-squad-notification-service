package com.patricia.notification;

import com.patricia.notification.application.usecase.SendNotificationUseCaseImpl;
import com.patricia.notification.domain.exceptions.InvalidNotificationException;
import com.patricia.notification.domain.exceptions.NotificationTypeDisabledException;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.out.NotificationDeliveryPort;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import com.patricia.notification.domain.ports.out.PreferencesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendNotificationUseCaseImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private PreferencesRepository preferencesRepository;

    @Mock
    private NotificationDeliveryPort notificationDeliveryPort;

    private SendNotificationUseCaseImpl useCase;

    private NotificationPreferences preferencesAllEnabled;

    @BeforeEach
    void setUp() {
        // Configurar el mock con lenient() para evitar errores de UnnecessaryStubbingException
        lenient().when(notificationDeliveryPort.supportedChannel()).thenReturn(NotificationChannel.IN_APP);
        
        // Instanciamos el use case manualmente y pasamos la lista con el mock del delivery port.
        useCase = new SendNotificationUseCaseImpl(notificationRepository, preferencesRepository, List.of(notificationDeliveryPort));

        preferencesAllEnabled = NotificationPreferences.builder()
                .userId("user-123")
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(true)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .build();
    }

    @Test
    @DisplayName("Debe crear, persistir y entregar la notificación correctamente")
    void execute_shouldSaveAndDeliverNotification() {
        Notification saved = Notification.builder()
                .id("notif-001")
                .userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Nuevo mensaje")
                .body("Juan te escribió")
                .read(false)
                .build();

        when(preferencesRepository.findByUserId("user-123"))
                .thenReturn(Optional.of(preferencesAllEnabled));
        when(notificationRepository.save(any())).thenReturn(saved);

        Notification result = useCase.execute(
                "user-123", NotificationType.PARCHE_MESSAGE,
                "Nuevo mensaje", "Juan te escribió", null);

        assertThat(result.getId()).isEqualTo("notif-001");
        assertThat(result.getUserId()).isEqualTo("user-123");
        assertThat(result.isRead()).isFalse();

        verify(notificationRepository).save(any(Notification.class));
        verify(notificationDeliveryPort).deliver(saved);
    }

    @Test
    @DisplayName("Si el usuario no tiene preferencias, debe usar las preferencias por defecto")
    void execute_shouldUseDefaultPreferences_whenNotFound() {
        when(preferencesRepository.findByUserId("user-nuevo"))
                .thenReturn(Optional.empty());

        Notification saved = Notification.builder()
                .id("notif-002")
                .userId("user-nuevo")
                .type(NotificationType.CONNECTION_REQUEST)
                .channel(NotificationChannel.IN_APP)
                .title("Nueva conexión")
                .body("Alguien quiere conectarse")
                .read(false)
                .build();

        when(notificationRepository.save(any())).thenReturn(saved);

        Notification result = useCase.execute(
                "user-nuevo", NotificationType.CONNECTION_REQUEST,
                "Nueva conexión", "Alguien quiere conectarse", null);

        assertThat(result).isNotNull();
        verify(notificationDeliveryPort).deliver(saved);
    }

    @Test
    @DisplayName("Debe guardar la notificación con channel IN_APP siempre")
    void execute_shouldAlwaysUseInAppChannel() {
        when(preferencesRepository.findByUserId("user-123"))
                .thenReturn(Optional.of(preferencesAllEnabled));

        ArgumentCaptor<Notification> captor = ArgumentCaptor.forClass(Notification.class);
        Notification saved = Notification.builder()
                .id("notif-003")
                .userId("user-123")
                .type(NotificationType.EVENT_REMINDER)
                .channel(NotificationChannel.IN_APP)
                .title("Recordatorio")
                .body("Tu evento es mañana")
                .read(false)
                .build();

        when(notificationRepository.save(captor.capture())).thenReturn(saved);

        useCase.execute("user-123", NotificationType.EVENT_REMINDER,
                "Recordatorio", "Tu evento es mañana", "evento-99");

        Notification persisted = captor.getValue();
        assertThat(persisted.getChannel()).isEqualTo(NotificationChannel.IN_APP);
        assertThat(persisted.getReferenceId()).isEqualTo("evento-99");
    }


    @Test
    @DisplayName("Debe lanzar InvalidNotificationException si userId es null")
    void execute_shouldThrow_whenUserIdIsNull() {
        assertThatThrownBy(() ->
                useCase.execute(null, NotificationType.PARCHE_MESSAGE, "título", "cuerpo", null))
                .isInstanceOf(InvalidNotificationException.class);

        verifyNoInteractions(notificationRepository, notificationDeliveryPort);
    }

    @Test
    @DisplayName("Debe lanzar InvalidNotificationException si userId está en blanco")
    void execute_shouldThrow_whenUserIdIsBlank() {
        assertThatThrownBy(() ->
                useCase.execute("   ", NotificationType.PARCHE_MESSAGE, "título", "cuerpo", null))
                .isInstanceOf(InvalidNotificationException.class);
    }

    @Test
    @DisplayName("Debe lanzar InvalidNotificationException si type es null")
    void execute_shouldThrow_whenTypeIsNull() {
        assertThatThrownBy(() ->
                useCase.execute("user-123", null, "título", "cuerpo", null))
                .isInstanceOf(InvalidNotificationException.class);
    }

    @Test
    @DisplayName("Debe lanzar InvalidNotificationException si body está en blanco")
    void execute_shouldThrow_whenBodyIsBlank() {
        assertThatThrownBy(() ->
                useCase.execute("user-123", NotificationType.PARCHE_MESSAGE, "título", "", null))
                .isInstanceOf(InvalidNotificationException.class);
    }


    @Test
    @DisplayName("Debe lanzar NotificationTypeDisabledException si el tipo está deshabilitado")
    void execute_shouldThrow_whenNotificationTypeDisabled() {
        NotificationPreferences prefsNearbyOff = NotificationPreferences.builder()
                .userId("user-123")
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .build();

        when(preferencesRepository.findByUserId("user-123"))
                .thenReturn(Optional.of(prefsNearbyOff));

        assertThatThrownBy(() ->
                useCase.execute("user-123", NotificationType.NEARBY_PARCHE,
                        "Parche cerca", "Hay un parche a 500m", null))
                .isInstanceOf(NotificationTypeDisabledException.class);

        verifyNoInteractions(notificationDeliveryPort);
    }

    @Test
    @DisplayName("NEARBY_PARCHE está deshabilitado en las preferencias por defecto")
    void execute_shouldThrow_forNearbyParche_withDefaultPreferences() {
        when(preferencesRepository.findByUserId("user-nuevo"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                useCase.execute("user-nuevo", NotificationType.NEARBY_PARCHE,
                        "Parche cerca", "Hay un parche a 500m", null))
                .isInstanceOf(NotificationTypeDisabledException.class);
    }
}
