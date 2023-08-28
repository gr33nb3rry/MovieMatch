package org.moviematchers.moviematch.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.moviematchers.moviematch.repository.UserRepository;
import org.moviematchers.moviematch.type.ErrorStatus;
import org.moviematchers.moviematch.type.Presence;

import org.springframework.stereotype.Component;

@Component
public class UserIdValidator implements ConstraintValidator<ValidUserId, Long> {
	private final UserRepository repository;
	private Presence presence;

	public UserIdValidator(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public void initialize(ValidUserId annotation) {
		this.presence = annotation.presence();
	}

	@Override
	public boolean isValid(Long id, ConstraintValidatorContext context) {
		if (id == null || id < 0) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_ID_INVALID.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
		}
		if (this.presence == Presence.IGNORE) {
			return true;
		}
		boolean result = this.repository.existsById(id);
		if (this.presence == Presence.PRESENT) {
			if (!result) {
				context.buildConstraintViolationWithTemplate(ErrorStatus.USER_ID_ABSENT.getKey()).addConstraintViolation();
				context.disableDefaultConstraintViolation();
				return false;
			}
		} else if (result) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_ID_PRESENT.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;
		}
		return true;
	}
}