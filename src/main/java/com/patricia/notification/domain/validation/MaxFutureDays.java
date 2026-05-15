package com.patricia.notification.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = MaxFutureDaysValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxFutureDays {
    String message() default "La fecha no puede superar {value} días en el futuro";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value() default 365;
}
