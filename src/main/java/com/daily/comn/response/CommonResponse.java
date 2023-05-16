package com.daily.comn.response;

import com.daily.comn.exception.dto.ErrorCode;
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
