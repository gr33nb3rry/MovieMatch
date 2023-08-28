package org.moviematchers.moviematch.validation;

import org.moviematchers.moviematch.dto.UserCredentials;
import org.moviematchers.moviematch.type.ErrorStatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUsernameCredentialsValidator implements ConstraintValidator<ValidUsername, UserCredentials> {
	private final UsernameValidator validator;

	public ValidUsernameCredentialsValidator(UsernameValidator validator) {
		this.validator = validator;
	}

	@Override
	public boolean isValid(UserCredentials credentials, ConstraintValidatorContext context) {
		if (credentials == null) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_USERNAME_ABSENT.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;
		}
		return this.validator.isValid(credentials.getUsername(), context);
	}
}
