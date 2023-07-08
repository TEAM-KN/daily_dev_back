package com.daily.auth.exception;

public class PasswordMatchException extends RuntimeException {
    public PasswordMatchException(final String message) {
        super(message);
    }

    public PasswordMatchException() {
        this("패스워드가 일치하지 않습니다.");
    }
}
