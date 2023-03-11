package com.daily.auth.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends RuntimeException {
    private static final String MESSAGE = "로그인이 필요한 서비스입니다.";

    private HttpStatus status;

    public InvalidTokenException() {
        super(MESSAGE);
        status = HttpStatus.UNAUTHORIZED;
    }

}
