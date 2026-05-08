package com.patricia.notification;

import com.patricia.notification.domain.model.Notification;
import com.patricia.notification.domain.model.enums.NotificationChannel;
import com.patricia.notification.domain.model.enums.NotificationType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationTest {

    @Test
    void markAsRead_shouldSetReadTrue() {
        Notification n = Notification.builder()
                .id("n1")
                .userId("u1")
                .type(NotificationType.PARCHE_MESSAGE)
                .channel(NotificationChannel.IN_APP)
                .title("t")
                .body("b")
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        assertThat(n.isRead()).isFalse();
        n.markAsRead();
        assertThat(n.isRead()).isTrue();
    }

    @Test
    void isInApp_shouldReturnTrueWhenChannelIsInApp() {
        Notification inApp = Notification.builder()
                .id("n2")
                .userId("u2")
                .type(NotificationType.EVENT_REMINDER)
                .channel(NotificationChannel.IN_APP)
                .build();

        Notification email = Notification.builder()
                .id("n3")
                .userId("someone@domain.com")
                .type(NotificationType.EVENT_REMINDER)
                .channel(NotificationChannel.EMAIL)
                .build();

        assertThat(inApp.isInApp()).isTrue();
        assertThat(email.isInApp()).isFalse();
    }
}

