package com.patricia.notification;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationDocument;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationPersistenceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationPersistenceMapperTest {

    private static final UUID NOTIF_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID NOTIF_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000011");
    private static final UUID NOTIF_ID_3 = UUID.fromString("00000000-0000-0000-0000-000000000012");
    private static final UUID USER_ID_1  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID USER_ID_3  = UUID.fromString("00000000-0000-0000-0000-000000000003");
    private static final UUID REF_ID_1   = UUID.fromString("00000000-0000-0000-0000-000000000020");
    private static final UUID REF_ID_2   = UUID.fromString("00000000-0000-0000-0000-000000000021");

    private NotificationPersistenceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new NotificationPersistenceMapper();
    }

    @Test
    @DisplayName("toDocument debe mapear todos los campos de Notification a NotificationDocument")
    void toDocument_shouldMapAllFields() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = Notification.builder()
                .id(NOTIF_ID_1)
                .userId(USER_ID_1)
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Nuevo mensaje")
                .body("Cuerpo del mensaje")
                .read(false)
                .referenceId(REF_ID_1)
                .createdAt(now)
                .build();

        NotificationDocument doc = mapper.toDocument(notification);

        assertThat(doc.getId()).isEqualTo(NOTIF_ID_1.toString());
        assertThat(doc.getUserId()).isEqualTo(USER_ID_1.toString());
        assertThat(doc.getType()).isEqualTo("PARCHE_MESSAGE");
        assertThat(doc.getChannel()).isEqualTo("IN_APP");
        assertThat(doc.getTitle()).isEqualTo("Nuevo mensaje");
        assertThat(doc.getBody()).isEqualTo("Cuerpo del mensaje");
        assertThat(doc.isRead()).isFalse();
        assertThat(doc.getReferenceId()).isEqualTo(REF_ID_1.toString());
        assertThat(doc.getCreatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("toDomain debe mapear todos los campos de NotificationDocument a Notification")
    void toDomain_shouldMapAllFields() {
        LocalDateTime now = LocalDateTime.now();
        NotificationDocument doc = NotificationDocument.builder()
                .id(NOTIF_ID_2.toString())
                .userId(USER_ID_2.toString())
                .type("CONNECTION_REQUEST")
                .channel("EMAIL")
                .title("Solicitud")
                .body("Cuerpo")
                .read(true)
                .referenceId(REF_ID_2.toString())
                .createdAt(now)
                .build();

        Notification notification = mapper.toDomain(doc);

        assertThat(notification.getId()).isEqualTo(NOTIF_ID_2);
        assertThat(notification.getUserId()).isEqualTo(USER_ID_2);
        assertThat(notification.getType()).isEqualTo(NotificationType.CONNECTION_REQUEST);
        assertThat(notification.getChannel()).isEqualTo(NotificationChannel.EMAIL);
        assertThat(notification.getTitle()).isEqualTo("Solicitud");
        assertThat(notification.getBody()).isEqualTo("Cuerpo");
        assertThat(notification.isRead()).isTrue();
        assertThat(notification.getReferenceId()).isEqualTo(REF_ID_2);
        assertThat(notification.getCreatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("toDomain debe manejar read=false correctamente")
    void toDomain_shouldHandleReadFalse() {
        NotificationDocument doc = NotificationDocument.builder()
                .id(NOTIF_ID_3.toString())
                .userId(USER_ID_3.toString())
                .type("EVENT_REMINDER")
                .channel("IN_APP")
                .title("Recordatorio")
                .body("Evento mañana")
                .read(false)
                .build();

        Notification notification = mapper.toDomain(doc);

        assertThat(notification.isRead()).isFalse();
        assertThat(notification.getType()).isEqualTo(NotificationType.EVENT_REMINDER);
    }
}
