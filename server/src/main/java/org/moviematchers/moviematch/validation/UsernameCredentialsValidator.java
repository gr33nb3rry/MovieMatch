package org.moviematchers.moviematch.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.moviematchers.moviematch.dto.UserCredentials;
import org.moviematchers.moviematch.type.ErrorStatus;

import org.springframework.stereotype.Component;

@Component
public class UsernameCredentialsValidator implements ConstraintValidator<ValidUsername, UserCredentials> {
	private final UsernameValidator validator;
	private ValidUsername annotation;

	public UsernameCredentialsValidator(UsernameValidator validator) {
		this.validator = validator;
	}

	@Override
	public void initialize(ValidUsername annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(UserCredentials credentials, ConstraintValidatorContext context) {
		if (credentials == null) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_USERNAME_INVALID.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;
		}
		this.validator.initialize(this.annotation);
		return this.validator.isValid(credentials.getUsername(), context);
	}
}
