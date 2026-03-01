package com.oop.library_management.validation.atleastonename;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneNameValidator.class)
@Documented
public @interface AtLeastOneName {

	String message() default "At least one of firstName or lastName must be provided";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}