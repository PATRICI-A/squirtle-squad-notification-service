package com.patricia.notification;

import com.patricia.notification.domain.model.EventReminder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EventReminderTest {

    private static final UUID USER_ID  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID EVENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Test
    void markReminded24h_shouldSetFlagTrue() {
        EventReminder reminder = EventReminder.builder()
                .id("r1")
                .userId(USER_ID)
                .eventId(EVENT_ID)
                .eventDate(LocalDateTime.now().plusDays(1))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        assertThat(reminder.isReminded24h()).isFalse();
        reminder.markReminded24h();
        assertThat(reminder.isReminded24h()).isTrue();
    }

    @Test
    void markReminded1h_shouldSetFlagTrue() {
        EventReminder reminder = EventReminder.builder()
                .id("r2")
                .userId(USER_ID)
                .eventId(EVENT_ID)
                .eventDate(LocalDateTime.now().plusHours(1))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        assertThat(reminder.isReminded1h()).isFalse();
        reminder.markReminded1h();
        assertThat(reminder.isReminded1h()).isTrue();
    }

    @Test
    void needs24hReminder_shouldReturnFalse_whenAlreadyReminded() {
        EventReminder reminder = EventReminder.builder()
                .id("r3")
                .userId(USER_ID)
                .eventId(EVENT_ID)
                .eventDate(LocalDateTime.now().plusHours(24))
                .reminded24h(true)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs24hReminder()).isFalse();
    }

    @Test
    void needs1hReminder_shouldReturnFalse_whenAlreadyReminded() {
        EventReminder reminder = EventReminder.builder()
                .id("r4")
                .userId(USER_ID)
                .eventId(EVENT_ID)
                .eventDate(LocalDateTime.now().plusHours(1))
                .reminded24h(false)
                .reminded1h(true)
                .build();

        assertThat(reminder.needs1hReminder()).isFalse();
    }

    @Test
    void needs24hReminder_shouldReturnFalse_whenEventIsNotInWindow() {
        EventReminder reminder = EventReminder.builder()
                .id("r5")
                .userId(USER_ID)
                .eventId(EVENT_ID)
                .eventDate(LocalDateTime.now().plusHours(10))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs24hReminder()).isFalse();
    }

    @Test
    void needs1hReminder_shouldReturnFalse_whenEventIsNotInWindow() {
        EventReminder reminder = EventReminder.builder()
                .id("r6")
                .userId(USER_ID)
                .eventId(EVENT_ID)
                .eventDate(LocalDateTime.now().plusHours(3))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs1hReminder()).isFalse();
    }

    @Test
    void needs24hReminder_shouldReturnTrue_whenEventIsExactlyIn24hWindow() {
        EventReminder reminder = EventReminder.builder()
                .id("r7")
                .userId(USER_ID)
                .eventId(EVENT_ID)
                .eventDate(LocalDateTime.now().plusMinutes(1440))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs24hReminder()).isTrue();
    }

    @Test
    void needs1hReminder_shouldReturnTrue_whenEventIsExactlyIn1hWindow() {
        EventReminder reminder = EventReminder.builder()
                .id("r8")
                .userId(USER_ID)
                .eventId(EVENT_ID)
                .eventDate(LocalDateTime.now().plusMinutes(60))
                .reminded24h(true)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs1hReminder()).isTrue();
    }
}
