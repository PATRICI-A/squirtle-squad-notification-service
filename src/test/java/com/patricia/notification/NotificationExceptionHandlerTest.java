package com.patricia.notification;

import com.patricia.notification.domain.exceptions.InvalidNotificationException;
import com.patricia.notification.domain.exceptions.NotificationDailyLimitException;
import com.patricia.notification.domain.exceptions.NotificationNotFoundException;
import com.patricia.notification.domain.exceptions.NotificationTypeDisabledException;
import com.patricia.notification.domain.model.enums.NotificationType;
import com.patricia.notification.entrypoints.advice.NotificationExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NotificationExceptionHandlerTest {

    private NotificationExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new NotificationExceptionHandler();
    }

    @Test
    @DisplayName("handleNotificationNotFound debe retornar 404 con mensaje")
    void handleNotificationNotFound_shouldReturn404() {
        NotificationNotFoundException ex = new NotificationNotFoundException("notif-123");
        ResponseEntity<Map<String, Object>> response = handler.handleNotificationNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).containsKey("message");
        assertThat(response.getBody().get("status")).isEqualTo(404);
        assertThat(response.getBody().get("error")).isEqualTo("Not Found");
        assertThat(response.getBody()).containsKey("timestamp");
    }

    @Test
    @DisplayName("handleTypeDisabled debe retornar 409")
    void handleTypeDisabled_shouldReturn409() {
        NotificationTypeDisabledException ex =
                new NotificationTypeDisabledException("user-1", NotificationType.NEARBY_PARCHE);
        ResponseEntity<Map<String, Object>> response = handler.handleTypeDisabled(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().get("status")).isEqualTo(409);
        assertThat(response.getBody().get("error")).isEqualTo("Conflict");
    }

    @Test
    @DisplayName("handleDailyLimit debe retornar 429")
    void handleDailyLimit_shouldReturn429() {
        NotificationDailyLimitException ex = new NotificationDailyLimitException("user-1");
        ResponseEntity<Map<String, Object>> response = handler.handleDailyLimit(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.TOO_MANY_REQUESTS);
        assertThat(response.getBody().get("status")).isEqualTo(429);
        assertThat(response.getBody().get("error")).isEqualTo("Too Many Requests");
    }

    @Test
    @DisplayName("handleInvalidNotification debe retornar 400")
    void handleInvalidNotification_shouldReturn400() {
        InvalidNotificationException ex = new InvalidNotificationException("campo inválido");
        ResponseEntity<Map<String, Object>> response = handler.handleInvalidNotification(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().get("status")).isEqualTo(400);
        assertThat(response.getBody().get("message").toString()).contains("campo inválido");
    }

    @Test
    @DisplayName("handleValidation debe retornar 400 con el campo del error")
    void handleValidation_shouldReturn400_withFieldError() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("obj", "userId", "no debe estar vacío");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, Object>> response = handler.handleValidation(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().get("message").toString()).contains("userId");
    }

    @Test
    @DisplayName("handleValidation debe retornar mensaje genérico si no hay field errors")
    void handleValidation_shouldReturn400_withGenericMessage_whenNoFieldErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of());

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, Object>> response = handler.handleValidation(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().get("message")).isEqualTo("Error de validación");
    }

    @Test
    @DisplayName("handleGeneric debe retornar 500")
    void handleGeneric_shouldReturn500() {
        Exception ex = new RuntimeException("Error inesperado");
        ResponseEntity<Map<String, Object>> response = handler.handleGeneric(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().get("status")).isEqualTo(500);
        assertThat(response.getBody().get("message")).isEqualTo("Error interno del servidor");
    }
}
