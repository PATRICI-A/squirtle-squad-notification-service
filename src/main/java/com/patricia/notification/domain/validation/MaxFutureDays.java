package com.patricia.notification.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that a {@link java.time.LocalDateTime} field does not exceed a maximum
 * number of days into the future.
 *
 * <p>Use alongside {@code @Future} to enforce both a lower bound (must be in the future)
 * and an upper bound (must not be too far ahead). Null values are considered valid;
 * combine with {@code @NotNull} to reject them.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 *   {@literal @}NotNull
 *   {@literal @}Future
 *   {@literal @}MaxFutureDays(NotificationRules.MAX_EVENT_FUTURE_DAYS)
 *   private LocalDateTime eventDate;
 * </pre>
 */
@Documented
@Constraint(validatedBy = MaxFutureDaysValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxFutureDays {
    String message() default "La fecha no puede superar {value} días en el futuro";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /** Maximum number of days from now the date may be. */
    int value() default 365;
}
