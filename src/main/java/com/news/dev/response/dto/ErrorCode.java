package com.news.dev.response.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS(200, "C200", "성공"),
    FAIL(999, "C999", "실패"),
    UNKONWN(000, "C000", "Not Found"),

    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    INVALID_TYPE_VALUE(400, "C002", " Invalid Type Value"),
    METHOD_NOT_ALLOWED(405, "C003", " Invalid Input Value"),

    ENTITY_NOT_FOUND(400, "C004", " Entity Not Found"),
    REQUEST_NOT_FOUND(404, "C005", "Http Request Handler Not Found"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    INTERNAL_SERVER_ERROR(500, "C500", "Server Error"),

    DATA_NOT_FOUND(100, "C100", "Data is not found"),
    USER_NOT_FOUND(101, "C101", "User is not found"),
    TOKEN_NOT_ACCESS(102, "C102", "Token is not Accessd");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
