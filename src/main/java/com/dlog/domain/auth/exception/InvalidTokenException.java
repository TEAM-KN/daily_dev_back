package com.dlog.domain.auth.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(final String message) {
        super(message);
    }

    public InvalidTokenException() {
        super("유효하지 않은 토큰입니다.");
    }

}
