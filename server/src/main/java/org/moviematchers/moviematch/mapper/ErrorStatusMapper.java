package org.moviematchers.moviematch.mapper;

import jakarta.validation.ConstraintViolation;

import org.moviematchers.moviematch.type.ErrorStatus;

import org.springframework.stereotype.Component;

@Component
public class ErrorStatusMapper implements Mapper<ConstraintViolation<?>, ErrorStatus> {
	@Override
	public ErrorStatus map(ConstraintViolation<?> violation) {
		String message = violation.getMessage();
		ErrorStatus status = ErrorStatus.fromKey(message);
		if (status == null) {
			status = ErrorStatus.INTERNAL;
		}
		return status;
	}
}
