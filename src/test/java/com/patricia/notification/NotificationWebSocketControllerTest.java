package com.patricia.notification;

import com.patricia.notification.entrypoints.rest.controller.NotificationWebSocketController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationWebSocketControllerTest {

    private final NotificationWebSocketController controller = new NotificationWebSocketController();

    @Test
    @DisplayName("handleConnect debe retornar mensaje con el userId conectado")
    void handleConnect_shouldReturnConnectionMessage() {
        String result = controller.handleConnect("user-123");

        assertThat(result).contains("user-123");
        assertThat(result).contains("conectado");
    }

    @Test
    @DisplayName("handleConnect debe incluir el userId exacto en el mensaje")
    void handleConnect_shouldIncludeExactUserId() {
        String userId = "usuario-especial-456";
        String result = controller.handleConnect(userId);

        assertThat(result).contains(userId);
    }
}
