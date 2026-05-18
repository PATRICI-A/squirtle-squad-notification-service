package com.patricia.notification;

import com.patricia.notification.domain.model.NotificationPreferences;
import com.patricia.notification.domain.model.enums.NotificationType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationPreferencesTest {

    @Test
    void isEnabled_shouldReturnCorrectFlags() {
        NotificationPreferences prefs = NotificationPreferences.builder()
                .userId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .connectionRequest(true)
                .parcheMessage(false)
                .eventReminder(true)
                .nearbyParche(false)
                .achievementUnlocked(true)
                .parcheInvitation(false)
                .otpVerification(true)
                .passwordReset(false)
                .invitationAccepted(true)
                .invitationSent(false)
                .memberJoined(true)
                .build();

        assertThat(prefs.isEnabled(NotificationType.CONNECTION_REQUEST)).isTrue();
        assertThat(prefs.isEnabled(NotificationType.PARCHE_MESSAGE)).isFalse();
        assertThat(prefs.isEnabled(NotificationType.EVENT_REMINDER)).isTrue();
        assertThat(prefs.isEnabled(NotificationType.NEARBY_PARCHE)).isFalse();
        assertThat(prefs.isEnabled(NotificationType.ACHIEVEMENT_UNLOCKED)).isTrue();
        assertThat(prefs.isEnabled(NotificationType.PARCHE_INVITATION)).isFalse();
        assertThat(prefs.isEnabled(NotificationType.OTP_VERIFICATION)).isTrue();
        assertThat(prefs.isEnabled(NotificationType.PASSWORD_RESET)).isFalse();
        assertThat(prefs.isEnabled(NotificationType.INVITATION_ACCEPTED)).isTrue();
        assertThat(prefs.isEnabled(NotificationType.INVITATION_SENT)).isFalse();
        assertThat(prefs.isEnabled(NotificationType.MEMBER_JOINED)).isTrue();
    }

    @Test
    void update_shouldToggleFlagAndSetUpdatedAt() {
        NotificationPreferences prefs = NotificationPreferences.builder()
                .userId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .connectionRequest(true)
                .parcheMessage(false)
                .build();

        // initial
        assertThat(prefs.isEnabled(NotificationType.PARCHE_MESSAGE)).isFalse();

        // update to true
        prefs.update(NotificationType.PARCHE_MESSAGE, true);
        assertThat(prefs.isEnabled(NotificationType.PARCHE_MESSAGE)).isTrue();
        assertThat(prefs.getUpdatedAt()).isNotNull();

        // update back to false
        LocalDateTime before = prefs.getUpdatedAt();
        prefs.update(NotificationType.PARCHE_MESSAGE, false);
        assertThat(prefs.isEnabled(NotificationType.PARCHE_MESSAGE)).isFalse();
        assertThat(prefs.getUpdatedAt()).isAfterOrEqualTo(before);
    }

    @Test
    void update_shouldUpdateAllNotificationTypes() {
        NotificationPreferences prefs = NotificationPreferences.builder()
                .userId(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                .connectionRequest(false)
                .parcheMessage(false)
                .eventReminder(false)
                .nearbyParche(false)
                .achievementUnlocked(false)
                .parcheInvitation(false)
                .otpVerification(false)
                .passwordReset(false)
                .build();

        prefs.update(NotificationType.CONNECTION_REQUEST, true);
        assertThat(prefs.isEnabled(NotificationType.CONNECTION_REQUEST)).isTrue();

        prefs.update(NotificationType.EVENT_REMINDER, true);
        assertThat(prefs.isEnabled(NotificationType.EVENT_REMINDER)).isTrue();

        prefs.update(NotificationType.NEARBY_PARCHE, true);
        assertThat(prefs.isEnabled(NotificationType.NEARBY_PARCHE)).isTrue();

        prefs.update(NotificationType.ACHIEVEMENT_UNLOCKED, true);
        assertThat(prefs.isEnabled(NotificationType.ACHIEVEMENT_UNLOCKED)).isTrue();

        prefs.update(NotificationType.PARCHE_INVITATION, true);
        assertThat(prefs.isEnabled(NotificationType.PARCHE_INVITATION)).isTrue();

        prefs.update(NotificationType.OTP_VERIFICATION, true);
        assertThat(prefs.isEnabled(NotificationType.OTP_VERIFICATION)).isTrue();

        prefs.update(NotificationType.PASSWORD_RESET, true);
        assertThat(prefs.isEnabled(NotificationType.PASSWORD_RESET)).isTrue();

        prefs.update(NotificationType.INVITATION_ACCEPTED, true);
        assertThat(prefs.isEnabled(NotificationType.INVITATION_ACCEPTED)).isTrue();

        prefs.update(NotificationType.INVITATION_SENT, true);
        assertThat(prefs.isEnabled(NotificationType.INVITATION_SENT)).isTrue();

        prefs.update(NotificationType.MEMBER_JOINED, true);
        assertThat(prefs.isEnabled(NotificationType.MEMBER_JOINED)).isTrue();
    }
}

