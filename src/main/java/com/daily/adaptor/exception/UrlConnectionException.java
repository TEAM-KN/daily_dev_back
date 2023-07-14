package com.daily.adaptor.exception;

public class UrlConnectionException extends IllegalArgumentException {
    public UrlConnectionException(final String message) {
        super(message);
    }

    public UrlConnectionException() {
        this("요청한 URL에 접근할 수 없습니다.");
    }
}
