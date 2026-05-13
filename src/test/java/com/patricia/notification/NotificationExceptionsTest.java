package com.patricia.notification;

import com.patricia.notification.domain.exceptions.InvalidNotificationException;
import com.patricia.notification.domain.exceptions.NotificationDailyLimitException;
import com.patricia.notification.domain.exceptions.NotificationNotFoundException;
import com.patricia.notification.domain.exceptions.NotificationTypeDisabledException;
import com.patricia.notification.domain.model.enums.NotificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NotificationExceptionsTest {

    private static final UUID NOTIF_ID = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID USER_ID  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID USER_ID3 = UUID.fromString("00000000-0000-0000-0000-000000000003");

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
        NotificationNotFoundException ex = new NotificationNotFoundException(NOTIF_ID);

        assertThat(ex).isInstanceOf(RuntimeException.class);
        assertThat(ex.getMessage()).contains(NOTIF_ID.toString());
    }

    @Test
    @DisplayName("NotificationTypeDisabledException debe contener userId y tipo")
    void notificationTypeDisabledException_shouldContainUserIdAndType() {
        NotificationTypeDisabledException ex = new NotificationTypeDisabledException(
                USER_ID2, NotificationType.NEARBY_PARCHE);

        assertThat(ex).isInstanceOf(RuntimeException.class);
        assertThat(ex.getMessage()).contains(USER_ID2.toString());
        assertThat(ex.getMessage()).contains("NEARBY_PARCHE");
    }

    @Test
    @DisplayName("NotificationDailyLimitException debe lanzarse con límite de notificaciones")
    void notificationDailyLimitException_shouldContainDailyLimit() {
        NotificationDailyLimitException ex = new NotificationDailyLimitException(USER_ID3);

        assertThat(ex).isInstanceOf(RuntimeException.class);
        assertThat(ex.getMessage()).contains(USER_ID3.toString());
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
            throw new NotificationNotFoundException(NOTIF_ID);
        }).isInstanceOf(NotificationNotFoundException.class)
         .hasMessageContaining(NOTIF_ID.toString());
    }
}
