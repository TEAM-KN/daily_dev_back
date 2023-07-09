package com.daily.fixtures;

import com.daily.global.common.response.CommonResponse;

public class CommonFixtures {

    public static final int code = 200;
    public static final String message = "성공";

    public static CommonResponse COMMON_RESPONSE() {
        return new CommonResponse(code, message);
    }

    public static CommonResponse COMMON_RESPONSE(final int code, final String message) {
        return new CommonResponse(code, message);
    }
}
