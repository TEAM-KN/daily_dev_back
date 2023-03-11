package com.daily.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExpiredTokenException extends RuntimeException {
    private static final String MESSAGE = "로그인이 필요한 서비스입니다.";

    private HttpStatus status;

    public ExpiredTokenException() {
        super(MESSAGE);
        status = HttpStatus.UNAUTHORIZED;
    }

}
