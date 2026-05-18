package com.patricia.notification;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.infrastructure.adapters.persistence.entity.NotificationPreferencesDocument;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.NotificationPreferencesPersistenceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationPreferencesPersistenceMapperTest {

    private static final UUID PREF_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID PREF_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000011");
    private static final UUID USER_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000002");

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
                .id(PREF_ID_1)
                .userId(USER_ID_1)
                .connectionRequest(true)
                .parcheMessage(false)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(false)
                .updatedAt(updatedAt)
                .build();

        NotificationPreferencesDocument doc = mapper.toDocument(preferences);

        assertThat(doc.getId()).isEqualTo(PREF_ID_1.toString());
        assertThat(doc.getUserId()).isEqualTo(USER_ID_1.toString());
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
                .id(PREF_ID_2.toString())
                .userId(USER_ID_2.toString())
                .connectionRequest(false)
                .parcheMessage(true)
                .eventReminder(false)
                .nearbyParche(true)
                .achievementUnlocked(false)
                .parcheInvitation(true)
                .updatedAt(updatedAt)
                .build();

        NotificationPreferences preferences = mapper.toDomain(doc);

        assertThat(preferences.getId()).isEqualTo(PREF_ID_2);
        assertThat(preferences.getUserId()).isEqualTo(USER_ID_2);
        assertThat(preferences.isConnectionRequest()).isFalse();
        assertThat(preferences.isParcheMessage()).isTrue();
        assertThat(preferences.isEventReminder()).isFalse();
        assertThat(preferences.isNearbyParche()).isTrue();
        assertThat(preferences.isAchievementUnlocked()).isFalse();
        assertThat(preferences.isParcheInvitation()).isTrue();
        assertThat(preferences.getUpdatedAt()).isEqualTo(updatedAt);
    }
}
