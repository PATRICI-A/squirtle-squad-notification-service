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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationMapperTest {

    private static final UUID NOTIF_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID NOTIF_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000011");
    private static final UUID USER_ID    = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID REF_ID     = UUID.fromString("00000000-0000-0000-0000-000000000020");

    private NotificationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new NotificationMapper();
    }

    @Test
    @DisplayName("toResponse: debe mapear todos los campos de Notification a NotificationResponse")
    void toResponse_shouldMapAllFields() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = Notification.builder()
                .id(NOTIF_ID_1)
                .userId(USER_ID)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Nuevo mensaje")
                .body("Juan te escribió")
                .read(false)
                .referenceId(REF_ID)
                .createdAt(now)
                .build();

        NotificationResponse response = mapper.toResponse(notification);

        assertThat(response.getId()).isEqualTo(NOTIF_ID_1);
        assertThat(response.getUserId()).isEqualTo(USER_ID);
        assertThat(response.getType()).isEqualTo(NotificationType.PARCHE_MESSAGE);
        assertThat(response.getChannel()).isEqualTo(NotificationChannel.IN_APP);
        assertThat(response.getTitle()).isEqualTo("Nuevo mensaje");
        assertThat(response.getBody()).isEqualTo("Juan te escribió");
        assertThat(response.isRead()).isFalse();
        assertThat(response.getReferenceId()).isEqualTo(REF_ID);
        assertThat(response.getCreatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("toResponse: debe mapear read=true correctamente")
    void toResponse_shouldMapReadTrue() {
        Notification notification = Notification.builder()
                .id(NOTIF_ID_2)
                .userId(USER_ID)
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
                .userId(USER_ID)
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
