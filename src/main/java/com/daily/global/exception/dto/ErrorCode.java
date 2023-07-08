package com.daily.global.exception.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    HANDLER_NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 핸들러를 찾을 수 없습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 형식의 데이터 입니다. 올바른 데이터 형식으로 요청해주세요."),
    INTERVAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생하였습니다. 관리자에게 문의해주세요."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메소드 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    CONFLICT(HttpStatus.CONFLICT, "이미 존재한 자원과 충돌되었습니다. 중복된 Key를 사용 할 수 없습니다."),
    TOKEN_EXPIRATION(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

    private HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
