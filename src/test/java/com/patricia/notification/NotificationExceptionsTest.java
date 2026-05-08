package com.patricia.notification;

import com.patricia.notification.domain.exceptions.InvalidNotificationException;
import com.patricia.notification.domain.exceptions.NotificationDailyLimitException;
import com.patricia.notification.domain.exceptions.NotificationNotFoundException;
import com.patricia.notification.domain.exceptions.NotificationTypeDisabledException;
import com.patricia.notification.domain.model.enums.NotificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NotificationExceptionsTest {

    @Test
    @DisplayName("InvalidNotificationException debe lanzarse con mensaje")
    void invalidNotificationException_shouldContainMessage() {
        String message = "El campo userId es obligatorio";
        InvalidNotificationException ex = new InvalidNotificationException(message);

        assertThat(ex).isInstanceOf(RuntimeException.class);
        assertThat(ex.getMessage()).contains(message);
    }

    @Test
    @DisplayName("NotificationNotFoundException debe lanzarse con id de notificación")
    void notificationNotFoundException_shouldContainNotificationId() {
        String notificationId = "notif-123";
        NotificationNotFoundException ex = new NotificationNotFoundException(notificationId);

        assertThat(ex).isInstanceOf(RuntimeException.class);
        assertThat(ex.getMessage()).contains(notificationId);
    }

    @Test
    @DisplayName("NotificationTypeDisabledException debe contener userId y tipo")
    void notificationTypeDisabledException_shouldContainUserIdAndType() {
        NotificationTypeDisabledException ex = new NotificationTypeDisabledException(
                "user-456", NotificationType.NEARBY_PARCHE);

        assertThat(ex).isInstanceOf(RuntimeException.class);
        assertThat(ex.getMessage()).contains("user-456");
        assertThat(ex.getMessage()).contains("NEARBY_PARCHE");
    }

    @Test
    @DisplayName("NotificationDailyLimitException debe lanzarse con límite de notificaciones")
    void notificationDailyLimitException_shouldContainDailyLimit() {
        NotificationDailyLimitException ex = new NotificationDailyLimitException("user-789");

        assertThat(ex).isInstanceOf(RuntimeException.class);
        assertThat(ex.getMessage()).contains("user-789");
        assertThat(ex.getMessage()).contains("límite diario");
    }

    @Test
    @DisplayName("InvalidNotificationException puede ser lanzada y atrapada")
    void invalidNotificationException_canBeThrownAndCaught() {
        assertThatThrownBy(() -> {
            throw new InvalidNotificationException("Error de validación");
        }).isInstanceOf(InvalidNotificationException.class)
         .hasMessageContaining("Error de validación");
    }

    @Test
    @DisplayName("NotificationNotFoundException puede ser lanzada y atrapada")
    void notificationNotFoundException_canBeThrownAndCaught() {
        assertThatThrownBy(() -> {
            throw new NotificationNotFoundException("notif-xyz");
        }).isInstanceOf(NotificationNotFoundException.class)
         .hasMessageContaining("notif-xyz");
    }
}

