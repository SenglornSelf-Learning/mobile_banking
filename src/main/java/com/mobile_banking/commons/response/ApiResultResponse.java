package com.mobile_banking.commons.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResultResponse<T> {

	private String res;

	private String msg;

	private Integer status;

	private LocalDateTime timestamp;

	private T payload;

	public static ApiResultResponse<Void> ok(String msg) {
		return ok(msg, null);
	}

	public static <T> ApiResultResponse<T> ok(String msg, T payload) {
		return of("ok", msg, HttpStatus.OK, payload);
	}

	public static <T> ApiResultResponse<T> created(String msg, T payload) {
		return of("ok", msg, HttpStatus.CREATED, payload);
	}

	public static ApiResultResponse<Void> error(HttpStatus status, String msg) {
		return of("error", msg, status, null);
	}

	private static <T> ApiResultResponse<T> of(
			String res,
			String msg,
			HttpStatus status,
			T payload) {
		return new ApiResultResponse<>(res, msg, status.value(), LocalDateTime.now(), payload);
	}
}
