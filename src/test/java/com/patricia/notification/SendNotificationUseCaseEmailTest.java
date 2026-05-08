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
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

class SendNotificationUseCaseEmailTest {

    private NotificationRepository notificationRepository;
    private PreferencesRepository preferencesRepository;

    @BeforeEach
    void setup() {
        notificationRepository = mock(NotificationRepository.class);
        preferencesRepository = mock(PreferencesRepository.class);
    }

    @Test
    void execute_shouldDeliverViaEmailPort_whenUserIdContainsAt() {
        NotificationDeliveryPort emailPort = mock(NotificationDeliveryPort.class);
        when(emailPort.supportedChannel()).thenReturn(NotificationChannel.EMAIL);

        Notification saved = Notification.builder()
                .id("e1")
                .userId("u@domain.com")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.EMAIL)
                .title("t")
                .body("b")
                .build();

        NotificationPreferences prefs = NotificationPreferences.builder()
                .userId("u@domain.com")
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .otpVerification(true)
                .passwordReset(true)
                .build();

        when(preferencesRepository.findByUserId("u@domain.com")).thenReturn(Optional.of(prefs));
        when(notificationRepository.save(any())).thenReturn(saved);

        SendNotificationUseCaseImpl useCase = new SendNotificationUseCaseImpl(
                notificationRepository, preferencesRepository, List.of(emailPort)
        );

        Notification result = useCase.execute("u@domain.com", NotificationType.PARCHE_MESSAGE,
                "titulo", "cuerpo", null);

        assertThat(result.getUserId()).isEqualTo("u@domain.com");
        verify(emailPort).deliver(saved);
    }

    @Test
    void execute_shouldNotDeliverWhenNoPortSupportsChannel() {
        NotificationDeliveryPort inAppPort = mock(NotificationDeliveryPort.class);
        when(inAppPort.supportedChannel()).thenReturn(NotificationChannel.IN_APP);

        Notification saved = Notification.builder()
                .id("e2")
                .userId("u@domain.com")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.EMAIL)
                .title("t")
                .body("b")
                .build();

        NotificationPreferences prefs = NotificationPreferences.builder()
                .userId("u@domain.com")
                .connectionRequest(true)
                .parcheMessage(true)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(true)
                .otpVerification(true)
                .passwordReset(true)
                .build();

        when(preferencesRepository.findByUserId("u@domain.com")).thenReturn(Optional.of(prefs));
        when(notificationRepository.save(any())).thenReturn(saved);

        SendNotificationUseCaseImpl useCase = new SendNotificationUseCaseImpl(
                notificationRepository, preferencesRepository, List.of(inAppPort)
        );

        useCase.execute("u@domain.com", NotificationType.PARCHE_MESSAGE,
                "titulo", "cuerpo", null);

        verify(inAppPort, never()).deliver(any());
    }
}

