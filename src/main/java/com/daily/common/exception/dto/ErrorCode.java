package com.daily.common.exception.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS(200, "200", "성공"),
    FAIL(999, "999", "실패"),
    UNKONWN(000, "000", "Not Found"),

    DUPLICATE_USER(409, "103", "사용자가 이미 존재합니다."),

    ENTITY_NOT_FOUND(400, "004", " Entity Not Found"),
    REQUEST_NOT_FOUND(404, "005", "Http Request Handler Not Found"),
    HANDLE_ACCESS_DENIED(403, "006", "Access is Denied"),

    INTERNAL_SERVER_ERROR(500, "500", "Server Error"),

    DATA_NOT_FOUND(100, "100", "Data is not found"),
    USER_NOT_FOUND(101, "101", "User is not found"),
    TOKEN_NOT_ACCESS(102, "102", "Token is not Accessd");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

}
