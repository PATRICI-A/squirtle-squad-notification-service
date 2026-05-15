package com.patricia.notification.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class MaxFutureDaysValidator implements ConstraintValidator<MaxFutureDays, LocalDateTime> {

    private int maxDays;

    @Override
    public void initialize(MaxFutureDays constraint) {
        this.maxDays = constraint.value();
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.isBefore(LocalDateTime.now().plusDays(maxDays));
    }
}
