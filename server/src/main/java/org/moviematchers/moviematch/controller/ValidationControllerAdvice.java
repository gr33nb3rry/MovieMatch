package org.moviematchers.moviematch.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.moviematchers.moviematch.dto.ErrorResponseBody;

import org.moviematchers.moviematch.dto.ErrorResponseBodyImpl;
import org.moviematchers.moviematch.mapper.Mapper;
import org.moviematchers.moviematch.type.ErrorStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Collection;

@ControllerAdvice
public class ValidationControllerAdvice {
	private final Mapper<ConstraintViolation<?>, ErrorStatus> mapper;

	public ValidationControllerAdvice(Mapper<ConstraintViolation<?>, ErrorStatus> mapper) {
		this.mapper = mapper;
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<ErrorResponseBody> handleValidation(ConstraintViolationException exception) {
		Collection<ErrorStatus> statuses = exception.getConstraintViolations()
			.stream()
			.map(this.mapper::map)
			.toList();
		ErrorResponseBody body = new ErrorResponseBodyImpl(statuses, Instant.now());
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(body);
	}

}
