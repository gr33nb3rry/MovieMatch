package org.moviematchers.moviematch.type;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorStatus {
	USER_ID_PRESENT("user.id.present"),
	USER_ID_ABSENT("user.id.absent"),
	USER_ID_INVALID("user.id.invalid"),
	USER_USERNAME_PRESENT("user.credentials.username.present"),
	USER_USERNAME_ABSENT("user.credentials.username.absent"),
	USER_USERNAME_INVALID("user.credentials.username.invalid"),
	USER_PASSWORD_INVALID("user.credentials.password.invalid"),
	INTERNAL("internal");

	private final static Map<String, ErrorStatus> statuses = Arrays
		.stream(ErrorStatus.values())
		.collect(
			Collectors.toMap(ErrorStatus::getKey, status -> status)
		);

	private final String key;
	ErrorStatus(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public int getCode() {
		return this.ordinal();
	}

	public static ErrorStatus fromKey(String key) {
		return ErrorStatus.statuses.get(key);
	}

}
