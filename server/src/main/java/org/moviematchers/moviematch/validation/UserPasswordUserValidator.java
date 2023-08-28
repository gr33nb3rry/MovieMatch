package org.moviematchers.moviematch.validation;

import org.moviematchers.moviematch.dto.User;
import org.moviematchers.moviematch.type.ErrorStatus;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UserPasswordUserValidator implements ConstraintValidator<ValidUserPassword, User> {
	private final UserPasswordCredentialsValidator validator;

	public UserPasswordUserValidator(UserPasswordCredentialsValidator validator) {
		this.validator = validator;
	}

	@Override
	public boolean isValid(User user, ConstraintValidatorContext context) {
		if (user == null) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_PASSWORD_INVALID.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;
		}
		return this.validator.isValid(user.getCredentials(), context);
	}
}
