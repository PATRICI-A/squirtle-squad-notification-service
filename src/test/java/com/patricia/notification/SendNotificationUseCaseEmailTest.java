package com.patricia.notification;

import com.patricia.notification.application.usecase.SendNotificationUseCaseImpl;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.domain.ports.out.NotificationDeliveryPort;
import com.patricia.notification.domain.ports.out.NotificationRepository;
import com.patricia.notification.domain.ports.out.PreferencesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

class SendNotificationUseCaseEmailTest {

    private static final UUID USER_ID  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID NOTIF_ID = UUID.fromString("00000000-0000-0000-0000-000000000010");

    private NotificationRepository notificationRepository;
    private PreferencesRepository preferencesRepository;

    @BeforeEach
    void setup() {
        notificationRepository = mock(NotificationRepository.class);
        preferencesRepository = mock(PreferencesRepository.class);
    }

    @Test
    void execute_shouldDeliverViaInAppPort_whenPortMatchesChannel() {
        NotificationDeliveryPort inAppPort = mock(NotificationDeliveryPort.class);
        when(inAppPort.supportedChannel()).thenReturn(NotificationChannel.IN_APP);

        Notification saved = Notification.builder()
                .id(NOTIF_ID)
                .userId(USER_ID)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("t")
                .body("b")
                .build();

        NotificationPreferences prefs = NotificationPreferences.builder()
                .userId(USER_ID)
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .otpVerification(true)
                .passwordReset(true)
                .build();

        when(preferencesRepository.findByUserId(USER_ID)).thenReturn(Optional.of(prefs));
        when(notificationRepository.save(any())).thenReturn(saved);

        SendNotificationUseCaseImpl useCase = new SendNotificationUseCaseImpl(
                notificationRepository, preferencesRepository, List.of(inAppPort)
        );

        Notification result = useCase.execute(USER_ID, NotificationType.PARCHE_MESSAGE,
                "titulo", "cuerpo", null);

        assertThat(result.getUserId()).isEqualTo(USER_ID);
        verify(inAppPort).deliver(saved);
    }

    @Test
    void execute_shouldNotDeliverWhenNoPortSupportsChannel() {
        NotificationDeliveryPort emailPort = mock(NotificationDeliveryPort.class);
        when(emailPort.supportedChannel()).thenReturn(NotificationChannel.EMAIL);

        Notification saved = Notification.builder()
                .id(NOTIF_ID)
                .userId(USER_ID)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("t")
                .body("b")
                .build();

        NotificationPreferences prefs = NotificationPreferences.builder()
                .userId(USER_ID)
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .otpVerification(true)
                .passwordReset(true)
                .build();

        when(preferencesRepository.findByUserId(USER_ID)).thenReturn(Optional.of(prefs));
        when(notificationRepository.save(any())).thenReturn(saved);

        SendNotificationUseCaseImpl useCase = new SendNotificationUseCaseImpl(
                notificationRepository, preferencesRepository, List.of(emailPort)
        );

        useCase.execute(USER_ID, NotificationType.PARCHE_MESSAGE,
                "titulo", "cuerpo", null);

        verify(emailPort, never()).deliver(any());
    }
}
