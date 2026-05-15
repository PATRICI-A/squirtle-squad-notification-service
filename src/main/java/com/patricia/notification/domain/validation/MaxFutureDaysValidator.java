package com.patricia.notification.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

/**
 * Constraint validator for the {@link MaxFutureDays} annotation.
 *
 * <p>Returns {@code true} when the value is {@code null} (null handling is delegated
 * to {@code @NotNull}) or when the date falls before the computed upper bound.</p>
 */
public class MaxFutureDaysValidator implements ConstraintValidator<MaxFutureDays, LocalDateTime> {

    private int maxDays;

    @Override
    public void initialize(MaxFutureDays constraint) {
        this.maxDays = constraint.value();
    }

    /**
     * @param value   the date to validate; {@code null} is treated as valid
     * @param context the validator context
     * @return {@code true} if the date is within {@code maxDays} from now
     */
    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.isBefore(LocalDateTime.now().plusDays(maxDays));
    }
}
