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

import static org.assertj.core.api.Assertions.assertThat;

class NotificationPersistenceMapperTest {

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
                .id("notif-001")
                .userId("user-123")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("Nuevo mensaje")
                .body("Cuerpo del mensaje")
                .read(false)
                .referenceId("ref-456")
                .createdAt(now)
                .build();

        NotificationDocument doc = mapper.toDocument(notification);

        assertThat(doc.getId()).isEqualTo("notif-001");
        assertThat(doc.getUserId()).isEqualTo("user-123");
        assertThat(doc.getType()).isEqualTo("PARCHE_MESSAGE");
        assertThat(doc.getChannel()).isEqualTo("IN_APP");
        assertThat(doc.getTitle()).isEqualTo("Nuevo mensaje");
        assertThat(doc.getBody()).isEqualTo("Cuerpo del mensaje");
        assertThat(doc.isRead()).isFalse();
        assertThat(doc.getReferenceId()).isEqualTo("ref-456");
        assertThat(doc.getCreatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("toDomain debe mapear todos los campos de NotificationDocument a Notification")
    void toDomain_shouldMapAllFields() {
        LocalDateTime now = LocalDateTime.now();
        NotificationDocument doc = NotificationDocument.builder()
                .id("notif-002")
                .userId("user-456")
                .type("CONNECTION_REQUEST")
                .channel("EMAIL")
                .title("Solicitud")
                .body("Cuerpo")
                .read(true)
                .referenceId("ref-789")
                .createdAt(now)
                .build();

        Notification notification = mapper.toDomain(doc);

        assertThat(notification.getId()).isEqualTo("notif-002");
        assertThat(notification.getUserId()).isEqualTo("user-456");
        assertThat(notification.getType()).isEqualTo(NotificationType.CONNECTION_REQUEST);
        assertThat(notification.getChannel()).isEqualTo(NotificationChannel.EMAIL);
        assertThat(notification.getTitle()).isEqualTo("Solicitud");
        assertThat(notification.getBody()).isEqualTo("Cuerpo");
        assertThat(notification.isRead()).isTrue();
        assertThat(notification.getReferenceId()).isEqualTo("ref-789");
        assertThat(notification.getCreatedAt()).isEqualTo(now);
    }

    @Test
    @DisplayName("toDomain debe manejar read=false correctamente")
    void toDomain_shouldHandleReadFalse() {
        NotificationDocument doc = NotificationDocument.builder()
                .id("notif-003")
                .userId("user-789")
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
