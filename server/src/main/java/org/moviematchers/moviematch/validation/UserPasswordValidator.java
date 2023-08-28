package org.moviematchers.moviematch.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import org.moviematchers.moviematch.type.ErrorStatus;

import org.springframework.stereotype.Component;

@Component
public class UserPasswordValidator implements ConstraintValidator<ValidUserPassword, String> {
	private final Pattern pattern = Pattern.compile("^[a-zA-Z0-9#?!@$ %^&*-_]{6,15}$");
	
	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		if (password == null || !pattern.matcher(password).matches()) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_PASSWORD_INVALID.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;
		}
		return true;
	}
}
