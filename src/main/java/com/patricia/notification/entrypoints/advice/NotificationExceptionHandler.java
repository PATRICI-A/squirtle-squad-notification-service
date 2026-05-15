package com.patricia.notification.entrypoints.advice;

import com.patricia.notification.domain.exceptions.InvalidNotificationException;
import com.patricia.notification.domain.exceptions.NotificationDailyLimitException;
import com.patricia.notification.domain.exceptions.NotificationNotFoundException;
import com.patricia.notification.domain.exceptions.NotificationTypeDisabledException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for all REST controllers in the notification service.
 *
 * <p>Translates domain exceptions and validation failures into structured JSON error
 * responses with a consistent body shape:
 * <pre>
 * {
 *   "timestamp": "...",
 *   "status": 400,
 *   "error": "Bad Request",
 *   "message": "..."
 * }
 * </pre>
 * </p>
 */
@RestControllerAdvice
public class NotificationExceptionHandler {

    /**
     * Handles lookups that return no result.
     * Maps to HTTP 404 Not Found.
     */
    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotificationNotFound(
            NotificationNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Handles attempts to send a notification type the user has disabled.
     * Maps to HTTP 409 Conflict.
     */
    @ExceptionHandler(NotificationTypeDisabledException.class)
    public ResponseEntity<Map<String, Object>> handleTypeDisabled(
            NotificationTypeDisabledException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Handles daily push notification limit breaches.
     * Maps to HTTP 429 Too Many Requests.
     */
    @ExceptionHandler(NotificationDailyLimitException.class)
    public ResponseEntity<Map<String, Object>> handleDailyLimit(
            NotificationDailyLimitException ex) {
        return buildResponse(HttpStatus.TOO_MANY_REQUESTS, ex.getMessage());
    }

    /**
     * Handles invalid notification data (null fields, blank strings, etc.).
     * Also triggered by {@link com.patricia.notification.domain.validation.EventDtoValidator}
     * for malformed RabbitMQ event DTOs.
     * Maps to HTTP 400 Bad Request.
     */
    @ExceptionHandler(InvalidNotificationException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidNotification(
            InvalidNotificationException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    /**
     * Handles Jakarta Bean Validation failures on {@code @RequestBody} parameters.
     * Returns the first constraint violation message.
     * Maps to HTTP 400 Bad Request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * Handles constraint violations on {@code @RequestParam} and {@code @PathVariable}
     * parameters when {@code @Validated} is applied at the controller class level.
     * Maps to HTTP 400 Bad Request.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(
            ConstraintViolationException ex) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .findFirst()
                .orElse("Invalid parameter");
        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    /**
     * Catch-all handler for unexpected exceptions.
     * Maps to HTTP 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error");
    }

    /**
     * Builds the standard error response body.
     *
     * @param status  the HTTP status to return
     * @param message the error description included in the response body
     * @return a structured {@link ResponseEntity} with timestamp, status, error, and message
     */
    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
