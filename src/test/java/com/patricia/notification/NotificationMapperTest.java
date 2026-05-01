package com.patricia.notification;

import com.patricia.notification.application.dto.response.NotificationPreferencesResponse;
import com.patricia.notification.application.dto.response.NotificationResponse;
import com.patricia.notification.application.dto.response.UnreadNotificationCountResponse;
import com.patricia.notification.application.mapper.NotificationMapper;
import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationMapperTest {

    private NotificationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new NotificationMapper();
    }



    @Test
    @DisplayName("toResponse: debe mapear todos los campos de Notification a NotificationResponse")
    void toResponse_shouldMapAllFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Notification notification = Notification.builder()
                .id("notif-001")
                .userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Nuevo mensaje")
                .body("Juan te escribió")
                .read(false)
                .referenceId("parche-456")
                .createdAt(now)
                .build();

        // Act
        NotificationResponse response = mapper.toResponse(notification);

        // Assert
        assertThat(response.getId()).isEqualTo("notif-001");
        assertThat(response.getUserId()).isEqualTo("user-123");
        assertThat(response.getType()).isEqualTo(NotificationType.PARCHE_MESSAGE);
        assertThat(response.getChannel()).isEqualTo(NotificationChannel.IN_APP);
        assertThat(response.getTitle()).isEqualTo("Nuevo mensaje");
        assertThat(response.getBody()).isEqualTo("Juan te escribió");
        assertThat(response.isRead()).isFalse();
        assertThat(response.getReferenceId()).isEqualTo("parche-456");
        assertThat(response.getCreatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("toResponse: debe mapear read=true correctamente")
    void toResponse_shouldMapReadTrue() {
        Notification notification = Notification.builder()
                .id("notif-002")
                .userId("user-123")
                .type(NotificationType.CONNECTION_REQUEST)
                .channel(NotificationChannel.IN_APP)
                .title("Conexión")
                .body("Cuerpo")
                .read(true)
                .build();

        NotificationResponse response = mapper.toResponse(notification);

        assertThat(response.isRead()).isTrue();
    }



    @Test
    @DisplayName("toPreferencesResponse: debe mapear todas las preferencias")
    void toPreferencesResponse_shouldMapAllFields() {
        NotificationPreferences preferences = NotificationPreferences.builder()
                .userId("user-123")
                .connectionRequest(true)
                .parcheMessage(false)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(false)
                .build();

        NotificationPreferencesResponse response = mapper.toPreferencesResponse(preferences);

        assertThat(response.isConnectionRequest()).isTrue();
        assertThat(response.isParcheMessage()).isFalse();
        assertThat(response.isEventReminder()).isTrue();
        assertThat(response.isNearbyParche()).isFalse();
        assertThat(response.isAchievementUnlocked()).isTrue();
        assertThat(response.isParcheInvitation()).isFalse();
    }



    @Test
    @DisplayName("toUnreadCountResponse: debe mapear el conteo correctamente")
    void toUnreadCountResponse_shouldMapCount() {
        UnreadNotificationCountResponse response = mapper.toUnreadCountResponse(7);

        assertThat(response.getCount()).isEqualTo(7);
    }

    @Test
    @DisplayName("toUnreadCountResponse: debe mapear cero correctamente")
    void toUnreadCountResponse_shouldMapZero() {
        UnreadNotificationCountResponse response = mapper.toUnreadCountResponse(0);

        assertThat(response.getCount()).isZero();
    }
}