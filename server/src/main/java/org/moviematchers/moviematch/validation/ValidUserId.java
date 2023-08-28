package org.moviematchers.moviematch.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.moviematchers.moviematch.type.Presence;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {
	UserIdValidator.class,
	UserIdUserValidator.class
})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserId {
	String message() default "user presence state not valid";

	Presence presence() default Presence.IGNORE;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}