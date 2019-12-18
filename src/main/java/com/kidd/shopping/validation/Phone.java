package com.kidd.shopping.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    String message() default "{com.kidd.shopping.validation.PhoneValidator.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    boolean nullable() default true;
}
