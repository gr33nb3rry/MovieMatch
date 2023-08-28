package org.moviematchers.moviematch.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.moviematchers.moviematch.dto.User;
import org.moviematchers.moviematch.type.ErrorStatus;

import org.springframework.stereotype.Component;

@Component
public class UsernameUserValidator implements ConstraintValidator<ValidUsername, User> {
	private final UsernameCredentialsValidator validator;
	private ValidUsername annotation;

	public UsernameUserValidator(UsernameCredentialsValidator validator) {
		this.validator = validator;
	}

	@Override
	public void initialize(ValidUsername annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(User user, ConstraintValidatorContext context) {
		if (user == null) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_USERNAME_INVALID.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;

		}
		this.validator.initialize(this.annotation);
		return this.validator.isValid(user.getCredentials(), context);
	}
}
