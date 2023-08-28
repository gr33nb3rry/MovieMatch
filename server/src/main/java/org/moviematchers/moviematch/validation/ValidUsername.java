package org.moviematchers.moviematch.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import org.moviematchers.moviematch.type.Presence;

@Documented
@Constraint(validatedBy = {
	UsernameValidator.class,
	UsernameCredentialsValidator.class,
	UsernameUserValidator.class
})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
	String message() default "user's username is invalid";

	Class<?>[] groups() default {};

	Presence presence() default Presence.IGNORE;

	Class<? extends Payload>[] payload() default {};
}