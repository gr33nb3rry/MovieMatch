package org.moviematchers.moviematch.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.moviematchers.moviematch.dto.User;
import org.moviematchers.moviematch.type.ErrorStatus;

import org.springframework.stereotype.Component;

@Component
public class UserIdUserValidator implements ConstraintValidator<ValidUserId, User> {
	private final UserIdValidator validator;
	private ValidUserId annotation;

	public UserIdUserValidator(UserIdValidator validator) {
		this.validator = validator;
	}

	@Override
	public void initialize(ValidUserId annotation) {
		this.annotation = annotation;
	}

	@Override
	public boolean isValid(User user, ConstraintValidatorContext context) {
		if (user == null) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_ID_ABSENT.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;
		}
		this.validator.initialize(this.annotation);
		return this.validator.isValid(user.getId(), context);
	}
}
