package com.patricia.notification;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationPreferencesDocument;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationPreferencesPersistenceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationPreferencesPersistenceMapperTest {

    private NotificationPreferencesPersistenceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new NotificationPreferencesPersistenceMapper();
    }

    @Test
    @DisplayName("toDocument debe mapear todos los campos de NotificationPreferences a NotificationPreferencesDocument")
    void toDocument_shouldMapAllFields() {
        LocalDateTime updatedAt = LocalDateTime.now();
        NotificationPreferences preferences = NotificationPreferences.builder()
                .id("pref-001")
                .userId("user-123")
                .connectionRequest(true)
                .parcheMessage(false)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(false)
                .updatedAt(updatedAt)
                .build();

        NotificationPreferencesDocument doc = mapper.toDocument(preferences);

        assertThat(doc.getId()).isEqualTo("pref-001");
        assertThat(doc.getUserId()).isEqualTo("user-123");
        assertThat(doc.isConnectionRequest()).isTrue();
        assertThat(doc.isParcheMessage()).isFalse();
        assertThat(doc.isEventReminder()).isTrue();
        assertThat(doc.isNearbyParche()).isFalse();
        assertThat(doc.isAchievementUnlocked()).isTrue();
        assertThat(doc.isParcheInvitation()).isFalse();
        assertThat(doc.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    @DisplayName("toDomain debe mapear todos los campos de NotificationPreferencesDocument a NotificationPreferences")
    void toDomain_shouldMapAllFields() {
        LocalDateTime updatedAt = LocalDateTime.now();
        NotificationPreferencesDocument doc = NotificationPreferencesDocument.builder()
                .id("pref-002")
                .userId("user-456")
                .connectionRequest(false)
                .parcheMessage(true)
                .eventReminder(false)
                .nearbyParche(true)
                .achievementUnlocked(false)
                .parcheInvitation(true)
                .updatedAt(updatedAt)
                .build();

        NotificationPreferences preferences = mapper.toDomain(doc);

        assertThat(preferences.getId()).isEqualTo("pref-002");
        assertThat(preferences.getUserId()).isEqualTo("user-456");
        assertThat(preferences.isConnectionRequest()).isFalse();
        assertThat(preferences.isParcheMessage()).isTrue();
        assertThat(preferences.isEventReminder()).isFalse();
        assertThat(preferences.isNearbyParche()).isTrue();
        assertThat(preferences.isAchievementUnlocked()).isFalse();
        assertThat(preferences.isParcheInvitation()).isTrue();
        assertThat(preferences.getUpdatedAt()).isEqualTo(updatedAt);
    }
}
