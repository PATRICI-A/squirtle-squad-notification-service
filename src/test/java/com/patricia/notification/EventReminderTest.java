package com.patricia.notification;

import com.patricia.notification.domain.model.EventReminder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EventReminderTest {

    @Test
    void needs24hReminder_shouldReturnTrueWhenWithinWindow() {
        EventReminder reminder = EventReminder.builder()
                .userId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .eventId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .eventDate(LocalDateTime.now().plusMinutes(24 * 60))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs24hReminder()).isTrue();
    }

    @Test
    void needs24hReminder_shouldReturnFalseIfAlreadyReminded() {
        EventReminder reminder = EventReminder.builder()
                .userId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .eventId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .eventDate(LocalDateTime.now().plusMinutes(24 * 60))
                .reminded24h(true)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs24hReminder()).isFalse();
    }

    @Test
    void needs1hReminder_shouldReturnTrueWhenWithinWindow() {
        EventReminder reminder = EventReminder.builder()
                .userId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .eventId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .eventDate(LocalDateTime.now().plusMinutes(60))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs1hReminder()).isTrue();
    }

    @Test
    void needs1hReminder_shouldReturnFalseIfAlreadyReminded() {
        EventReminder reminder = EventReminder.builder()
                .userId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .eventId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .eventDate(LocalDateTime.now().plusMinutes(60))
                .reminded24h(false)
                .reminded1h(true)
                .build();

        assertThat(reminder.needs1hReminder()).isFalse();
    }

    @Test
    void markReminded24h_shouldSetFlag() {
        EventReminder reminder = EventReminder.builder()
                .userId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .eventId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .eventDate(LocalDateTime.now().plusDays(2))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        reminder.markReminded24h();
        assertThat(reminder.isReminded24h()).isTrue();
    }

    @Test
    void markReminded1h_shouldSetFlag() {
        EventReminder reminder = EventReminder.builder()
                .userId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .eventId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .eventDate(LocalDateTime.now().plusDays(2))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        reminder.markReminded1h();
        assertThat(reminder.isReminded1h()).isTrue();
    }
}