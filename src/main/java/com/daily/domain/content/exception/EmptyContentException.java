package com.daily.domain.content.exception;

public class EmptyContentException extends RuntimeException {

    public EmptyContentException(final String message) {
        super(message);
    }

    public EmptyContentException() {
        this("컨텐츠 정보가 없습니다.");
    }
}
