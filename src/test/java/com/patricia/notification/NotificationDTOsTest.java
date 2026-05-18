package com.patricia.notification;

import com.patricia.notification.application.dto.response.NotificationResponse;
import com.patricia.notification.application.dto.response.NotificationPreferencesResponse;
import com.patricia.notification.application.dto.response.UnreadNotificationCountResponse;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationDTOsTest {

    private static final UUID NOTIF_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID NOTIF_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000011");
    private static final UUID NOTIF_ID_3 = UUID.fromString("00000000-0000-0000-0000-000000000012");
    private static final UUID NOTIF_ID_4 = UUID.fromString("00000000-0000-0000-0000-000000000013");
    private static final UUID USER_ID    = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID REF_ID     = UUID.fromString("00000000-0000-0000-0000-000000000020");

    @Test
    @DisplayName("NotificationResponse debe mapear todos los campos")
    void notificationResponse_shouldMapAllFields() {
        LocalDateTime now = LocalDateTime.now();
        NotificationResponse response = NotificationResponse.builder()
                .id(NOTIF_ID_1)
                .userId(USER_ID)
                .type(NotificationType.EVENT_REMINDER)
                .channel(NotificationChannel.IN_APP)
                .title("Recordatorio")
                .body("Tu evento es mañana")
                .read(false)
                .referenceId(REF_ID)
                .createdAt(now)
                .build();

        assertThat(response.getId()).isEqualTo(NOTIF_ID_1);
        assertThat(response.getUserId()).isEqualTo(USER_ID);
        assertThat(response.getType()).isEqualTo(NotificationType.EVENT_REMINDER);
        assertThat(response.getChannel()).isEqualTo(NotificationChannel.IN_APP);
        assertThat(response.getTitle()).isEqualTo("Recordatorio");
        assertThat(response.getBody()).isEqualTo("Tu evento es mañana");
        assertThat(response.isRead()).isFalse();
        assertThat(response.getReferenceId()).isEqualTo(REF_ID);
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
                .id(NOTIF_ID_2)
                .userId(USER_ID_2)
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
                .id(NOTIF_ID_3)
                .type(NotificationType.CONNECTION_REQUEST)
                .channel(NotificationChannel.IN_APP)
                .build();

        NotificationResponse response2 = NotificationResponse.builder()
                .id(NOTIF_ID_4)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.EMAIL)
                .build();

        assertThat(response1.getType()).isEqualTo(NotificationType.CONNECTION_REQUEST);
        assertThat(response2.getType()).isEqualTo(NotificationType.PARCHE_MESSAGE);
        assertThat(response2.getChannel()).isEqualTo(NotificationChannel.EMAIL);
    }
}
