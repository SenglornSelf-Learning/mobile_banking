package com.mobile_banking.commons.response;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseBody<T> {

	private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

	private boolean status;
	private int statusCode;
	private String message;
	private LocalDateTime timestamp;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public ResponseBody(boolean status, int statusCode, String message) {
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
		this.timestamp = LocalDateTime.now(SEOUL);
	}

	public static <T> ResponseBody<T> ok(String message, T data) {
		ResponseBody<T> body = new ResponseBody<>();
		body.status = true;
		body.statusCode = 200;
		body.message = message;
		body.timestamp = LocalDateTime.now(SEOUL);
		body.data = data;
		return body;
	}
}
