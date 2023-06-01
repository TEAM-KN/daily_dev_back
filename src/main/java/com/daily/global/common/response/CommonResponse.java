package com.daily.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {

    int code;
    String name;
    String message;

    public CommonResponse(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.name = httpStatus.name();
        this.message = message;
    }

}
