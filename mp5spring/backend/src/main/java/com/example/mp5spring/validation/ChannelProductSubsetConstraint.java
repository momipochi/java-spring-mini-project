package com.example.mp5spring.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ChannelProductSubsetValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChannelProductSubsetConstraint {
    String message() default "Invalid subset association";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
