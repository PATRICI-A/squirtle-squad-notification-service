package com.patricia.notification;

import com.patricia.notification.domain.model.EventReminder;
import com.patricia.notification.infrastructure.adapters.persistence.entity.EventReminderDocument;
import com.patricia.notification.infrastructure.adapters.persistence.mapper.EventReminderPersistenceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EventReminderPersistenceMapperTest {

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
                .userId("user-123")
                .eventId("event-456")
                .eventDate(eventDate)
                .reminded24h(false)
                .reminded1h(true)
                .build();

        EventReminderDocument doc = mapper.toDocument(reminder);

        assertThat(doc.getId()).isEqualTo("reminder-001");
        assertThat(doc.getUserId()).isEqualTo("user-123");
        assertThat(doc.getEventId()).isEqualTo("event-456");
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
                .userId("user-789")
                .eventId("event-789")
                .eventDate(eventDate)
                .reminded24h(true)
                .reminded1h(false)
                .build();

        EventReminder reminder = mapper.toDomain(doc);

        assertThat(reminder.getId()).isEqualTo("reminder-002");
        assertThat(reminder.getUserId()).isEqualTo("user-789");
        assertThat(reminder.getEventId()).isEqualTo("event-789");
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
                .userId("user-111")
                .eventId("event-111")
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
