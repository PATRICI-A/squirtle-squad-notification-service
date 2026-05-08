package com.patricia.notification;

import com.patricia.notification.domain.model.EventReminder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EventReminderTest {

    @Test
    void markReminded24h_shouldSetFlagTrue() {
        EventReminder reminder = EventReminder.builder()
                .id("r1")
                .userId("u1")
                .eventId("e1")
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
                .userId("u2")
                .eventId("e2")
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
                .userId("u3")
                .eventId("e3")
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
                .userId("u4")
                .eventId("e4")
                .eventDate(LocalDateTime.now().plusHours(1))
                .reminded24h(false)
                .reminded1h(true)
                .build();

        assertThat(reminder.needs1hReminder()).isFalse();
    }

    @Test
    void needs24hReminder_shouldReturnFalse_whenEventIsNotInWindow() {
        // Evento en 10 horas (fuera de la ventana de 24 horas ± 30 minutos)
        EventReminder reminder = EventReminder.builder()
                .id("r5")
                .userId("u5")
                .eventId("e5")
                .eventDate(LocalDateTime.now().plusHours(10))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs24hReminder()).isFalse();
    }

    @Test
    void needs1hReminder_shouldReturnFalse_whenEventIsNotInWindow() {
        // Evento en 3 horas (fuera de la ventana de 1 hora ± 30 minutos)
        EventReminder reminder = EventReminder.builder()
                .id("r6")
                .userId("u6")
                .eventId("e6")
                .eventDate(LocalDateTime.now().plusHours(3))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs1hReminder()).isFalse();
    }

    @Test
    void needs24hReminder_shouldReturnTrue_whenEventIsExactlyIn24hWindow() {
        // Evento exactamente a 24 horas (dentro de la ventana ± 30 minutos)
        EventReminder reminder = EventReminder.builder()
                .id("r7")
                .userId("u7")
                .eventId("e7")
                .eventDate(LocalDateTime.now().plusMinutes(1440))
                .reminded24h(false)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs24hReminder()).isTrue();
    }

    @Test
    void needs1hReminder_shouldReturnTrue_whenEventIsExactlyIn1hWindow() {
        // Evento exactamente a 1 hora (dentro de la ventana ± 30 minutos)
        EventReminder reminder = EventReminder.builder()
                .id("r8")
                .userId("u8")
                .eventId("e8")
                .eventDate(LocalDateTime.now().plusMinutes(60))
                .reminded24h(true)
                .reminded1h(false)
                .build();

        assertThat(reminder.needs1hReminder()).isTrue();
    }
}

