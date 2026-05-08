package com.patricia.notification;

import com.patricia.notification.application.dto.response.NotificationResponse;
import com.patricia.notification.application.dto.response.NotificationPreferencesResponse;
import com.patricia.notification.application.dto.response.UnreadNotificationCountResponse;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationDTOsTest {

    @Test
    @DisplayName("NotificationResponse debe mapear todos los campos")
    void notificationResponse_shouldMapAllFields() {
        LocalDateTime now = LocalDateTime.now();
        NotificationResponse response = NotificationResponse.builder()
                .id("n1")
                .userId("user-123")
                .type(NotificationType.EVENT_REMINDER)
                .channel(NotificationChannel.IN_APP)
                .title("Recordatorio")
                .body("Tu evento es mañana")
                .read(false)
                .referenceId("evt-999")
                .createdAt(now)
                .build();

        assertThat(response.getId()).isEqualTo("n1");
        assertThat(response.getUserId()).isEqualTo("user-123");
        assertThat(response.getType()).isEqualTo(NotificationType.EVENT_REMINDER);
        assertThat(response.getChannel()).isEqualTo(NotificationChannel.IN_APP);
        assertThat(response.getTitle()).isEqualTo("Recordatorio");
        assertThat(response.getBody()).isEqualTo("Tu evento es mañana");
        assertThat(response.isRead()).isFalse();
        assertThat(response.getReferenceId()).isEqualTo("evt-999");
        assertThat(response.getCreatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("NotificationPreferencesResponse debe contener todas las preferencias")
    void notificationPreferencesResponse_shouldContainAll() {
        NotificationPreferencesResponse response = NotificationPreferencesResponse.builder()
                .connectionRequest(true)
                .parcheMessage(false)
                .eventReminder(true)
                .nearbyParche(true)
                .achievementUnlocked(false)
                .parcheInvitation(true)
                .build();

        assertThat(response.isConnectionRequest()).isTrue();
        assertThat(response.isParcheMessage()).isFalse();
        assertThat(response.isEventReminder()).isTrue();
        assertThat(response.isNearbyParche()).isTrue();
        assertThat(response.isAchievementUnlocked()).isFalse();
        assertThat(response.isParcheInvitation()).isTrue();
    }

    @Test
    @DisplayName("UnreadNotificationCountResponse debe retornar count")
    void unreadNotificationCountResponse_shouldReturnCount() {
        UnreadNotificationCountResponse response = UnreadNotificationCountResponse.builder()
                .count(42)
                .build();

        assertThat(response.getCount()).isEqualTo(42);
    }

    @Test
    @DisplayName("NotificationResponse con read=true")
    void notificationResponse_readTrue_shouldWork() {
        NotificationResponse response = NotificationResponse.builder()
                .id("n2")
                .userId("u2")
                .type(NotificationType.ACHIEVEMENT_UNLOCKED)
                .channel(NotificationChannel.IN_APP)
                .title("Logro")
                .body("Desbloqueaste un logro")
                .read(true)
                .build();

        assertThat(response.isRead()).isTrue();
    }

    @Test
    @DisplayName("NotificationResponse diferentes tipos")
    void notificationResponse_differentTypes_shouldWork() {
        NotificationResponse response1 = NotificationResponse.builder()
                .id("n3")
                .type(NotificationType.CONNECTION_REQUEST)
                .channel(NotificationChannel.IN_APP)
                .build();

        NotificationResponse response2 = NotificationResponse.builder()
                .id("n4")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.EMAIL)
                .build();

        assertThat(response1.getType()).isEqualTo(NotificationType.CONNECTION_REQUEST);
        assertThat(response2.getType()).isEqualTo(NotificationType.PARCHE_MESSAGE);
        assertThat(response2.getChannel()).isEqualTo(NotificationChannel.EMAIL);
    }
}

