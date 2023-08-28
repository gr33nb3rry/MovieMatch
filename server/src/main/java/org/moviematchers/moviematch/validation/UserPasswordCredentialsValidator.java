package org.moviematchers.moviematch.validation;

import org.moviematchers.moviematch.dto.UserCredentials;
import org.moviematchers.moviematch.type.ErrorStatus;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UserPasswordCredentialsValidator implements ConstraintValidator<ValidUserPassword, UserCredentials> {
	private final UserPasswordValidator validator;

	public UserPasswordCredentialsValidator(UserPasswordValidator validator) {
		this.validator = validator;
	}

	@Override
	public boolean isValid(UserCredentials credentials, ConstraintValidatorContext context) {
		if (credentials == null) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_PASSWORD_INVALID.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;
		}
		return this.validator.isValid(credentials.getPassword(), context);
	}
}
