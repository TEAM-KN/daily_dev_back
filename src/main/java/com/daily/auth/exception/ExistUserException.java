package com.daily.auth.exception;

public class ExistUserException extends RuntimeException {

    public ExistUserException(final String message) {
        super(message);
    }

    public ExistUserException() {
        this("이미 사용 중인 이메일입니다.");
    }
}
