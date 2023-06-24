package com.daily.global.exception.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    HANDLER_NOT_FOUND(HttpStatus.NOT_FOUND, "Handler is not found"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    INTERVAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server error"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed"),
    NO_SEARCH_USER(HttpStatus.UNAUTHORIZED, "No Search User"),
    CONFLICT(HttpStatus.CONFLICT, "Resource Conflict"),
    SESSION_EXPIRATION(HttpStatus.UNAUTHORIZED, "Unauthorized User"),
    TOKEN_EXPIRATION(HttpStatus.UNAUTHORIZED, "Unauthorized User");

    private HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
