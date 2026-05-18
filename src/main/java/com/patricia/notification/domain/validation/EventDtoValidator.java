package com.patricia.notification.domain.validation;

import com.patricia.notification.domain.exceptions.InvalidNotificationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Programmatic validator for messaging event DTOs consumed from RabbitMQ queues.
 *
 * <p>RabbitMQ listeners do not trigger Jakarta Bean Validation automatically, so
 * each consumer calls {@link #validate(Object)} as the first step of message handling.
 * On failure, an {@link InvalidNotificationException} is thrown, causing the message
 * to be rejected and routed to the corresponding dead-letter queue.</p>
 */
@Component
public class EventDtoValidator {

    private final Validator validator;

    public EventDtoValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * Validates all Jakarta constraint annotations on the given DTO.
     *
     * @param dto the event DTO to validate
     * @param <T> type of the DTO
     * @throws InvalidNotificationException if one or more constraints are violated,
     *         with a message listing each failing field and its constraint message
     */
    public <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            throw new InvalidNotificationException(message);
        }
    }
}
