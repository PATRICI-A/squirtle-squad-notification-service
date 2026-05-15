package com.patricia.notification.domain.validation;

import com.patricia.notification.domain.exceptions.InvalidNotificationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EventDtoValidator {

    private final Validator validator;

    public EventDtoValidator(Validator validator) {
        this.validator = validator;
    }

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
