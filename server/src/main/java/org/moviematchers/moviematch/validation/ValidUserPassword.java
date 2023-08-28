package org.moviematchers.moviematch.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {
	UserPasswordValidator.class,
	UserPasswordCredentialsValidator.class,
	UserPasswordUserValidator.class
})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserPassword {
	String message() default "user's password is invalid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}