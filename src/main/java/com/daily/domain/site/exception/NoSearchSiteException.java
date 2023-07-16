package com.daily.domain.site.exception;

public class NoSearchSiteException extends RuntimeException {

    public NoSearchSiteException(final String message) {
        super(message);
    }

    public NoSearchSiteException() {
        this("사이트 정보를 찾을 수 없습니다.");
    }
}
