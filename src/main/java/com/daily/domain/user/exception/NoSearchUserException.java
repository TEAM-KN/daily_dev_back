package com.daily.domain.user.exception;

public class NoSearchUserException extends RuntimeException {

    public NoSearchUserException(final String message) {
        super(message);
    }

    public NoSearchUserException() {
        this("사용자를 찾을 수 없습니다.");
    }

}
