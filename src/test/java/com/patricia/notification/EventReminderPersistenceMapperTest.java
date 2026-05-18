package com.patricia.notification;

import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.infrastructure.adapters.persistence.entity.EventReminderDocument;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.EventReminderPersistenceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EventReminderPersistenceMapperTest {

    private static final UUID USER_ID_1  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID_2  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID USER_ID_3  = UUID.fromString("00000000-0000-0000-0000-000000000003");
    private static final UUID EVENT_ID_1 = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID EVENT_ID_2 = UUID.fromString("00000000-0000-0000-0000-000000000011");
    private static final UUID EVENT_ID_3 = UUID.fromString("00000000-0000-0000-0000-000000000012");

    private EventReminderPersistenceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new EventReminderPersistenceMapper();
    }

    @Test
    @DisplayName("toDocument debe mapear todos los campos de EventReminder a EventReminderDocument")
    void toDocument_shouldMapAllFields() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(1);
        EventReminder reminder = EventReminder.builder()
                .id("reminder-001")
                .userId(USER_ID_1)
                .eventId(EVENT_ID_1)
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(true)
                .build();

        EventReminderDocument doc = mapper.toDocument(reminder);

        assertThat(doc.getId()).isEqualTo("reminder-001");
        assertThat(doc.getUserId()).isEqualTo(USER_ID_1.toString());
        assertThat(doc.getEventId()).isEqualTo(EVENT_ID_1.toString());
        assertThat(doc.getEventDate()).isEqualTo(eventDate);
        assertThat(doc.isReminded24h()).isFalse();
        assertThat(doc.isReminded1h()).isTrue();
    }

    @Test
    @DisplayName("toDomain debe mapear todos los campos de EventReminderDocument a EventReminder")
    void toDomain_shouldMapAllFields() {
        LocalDateTime eventDate = LocalDateTime.now().plusDays(2);
        EventReminderDocument doc = EventReminderDocument.builder()
                .id("reminder-002")
                .userId(USER_ID_2.toString())
                .eventId(EVENT_ID_2.toString())
                .eventDate(eventDate)
                .reminded24h(true)
                .reminded1h(false)
                .build();

        EventReminder reminder = mapper.toDomain(doc);

        assertThat(reminder.getId()).isEqualTo("reminder-002");
        assertThat(reminder.getUserId()).isEqualTo(USER_ID_2);
        assertThat(reminder.getEventId()).isEqualTo(EVENT_ID_2);
        assertThat(reminder.getEventDate()).isEqualTo(eventDate);
        assertThat(reminder.isReminded24h()).isTrue();
        assertThat(reminder.isReminded1h()).isFalse();
    }

    @Test
    @DisplayName("toDocument y toDomain deben ser inversos")
    void toDocument_andToDomain_shouldBeInverse() {
        LocalDateTime eventDate = LocalDateTime.now().plusHours(48);
        EventReminder original = EventReminder.builder()
                .id("reminder-003")
                .userId(USER_ID_3)
                .eventId(EVENT_ID_3)
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(false)
                .build();

        EventReminder roundTripped = mapper.toDomain(mapper.toDocument(original));

        assertThat(roundTripped.getId()).isEqualTo(original.getId());
        assertThat(roundTripped.getUserId()).isEqualTo(original.getUserId());
        assertThat(roundTripped.getEventId()).isEqualTo(original.getEventId());
        assertThat(roundTripped.getEventDate()).isEqualTo(original.getEventDate());
        assertThat(roundTripped.isReminded24h()).isEqualTo(original.isReminded24h());
        assertThat(roundTripped.isReminded1h()).isEqualTo(original.isReminded1h());
    }
}
