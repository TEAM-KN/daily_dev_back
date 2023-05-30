package com.daily.common.response;

import com.daily.common.exception.dto.ErrorCode;
import lombok.Data;

@Data
public class CommonResponse {

    String code;
    String message;

    public CommonResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public CommonResponse(ErrorCode errorCode, String message) {
        this.code = errorCode.getCode();
        this.message = message;
    }

}
