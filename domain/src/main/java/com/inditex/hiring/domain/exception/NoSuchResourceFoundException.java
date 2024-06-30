package com.inditex.hiring.domain.exception;

import java.io.Serial;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchResourceFoundException extends RuntimeException {
	@Serial
    private static final long serialVersionUID = -8749643454264131447L;

	public NoSuchResourceFoundException(String msg) {
        super(msg);
    }
}
