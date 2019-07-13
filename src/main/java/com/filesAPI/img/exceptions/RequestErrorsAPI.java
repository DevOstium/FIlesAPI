package com.filesAPI.img.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequestErrorsAPI extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequestErrorsAPI(String message) {
		super(message);
	}

}
