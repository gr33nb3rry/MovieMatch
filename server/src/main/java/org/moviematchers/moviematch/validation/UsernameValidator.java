package org.moviematchers.moviematch.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import org.moviematchers.moviematch.repository.UserRepository;
import org.moviematchers.moviematch.type.ErrorStatus;
import org.moviematchers.moviematch.type.Presence;
import org.springframework.stereotype.Component;

@Component
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
	private final Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]{3,15}$");
	private final UserRepository repository;
	private Presence presence;

	public UsernameValidator(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public void initialize(ValidUsername annotation) {
		this.presence = annotation.presence();
	}
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		if (username == null || !pattern.matcher(username).matches()) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_USERNAME_INVALID.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;
		}
		if (this.presence == Presence.IGNORE) {
			return true;
		}
		boolean result = this.repository.existsByUsernameIgnoreCase(username);
		if (this.presence == Presence.PRESENT) {
			if (!result) {
				context.buildConstraintViolationWithTemplate(ErrorStatus.USER_USERNAME_ABSENT.getKey()).addConstraintViolation();
				context.disableDefaultConstraintViolation();
				return false;
			}
		} else if (result) {
			context.buildConstraintViolationWithTemplate(ErrorStatus.USER_USERNAME_PRESENT.getKey()).addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;
		}
		return true;
	}
}
