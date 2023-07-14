package com.daily.auth.application;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class AuthExtractor {
    private AuthExtractor() {}

    public static final String AUTH_TYPE = "Bearer ";
    public static final String AUTH_HEADER_KEY = "Authorization";

    public static String extractAccessToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTH_HEADER_KEY);

        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (value.toLowerCase().startsWith(AUTH_TYPE.toLowerCase())) {
                return value.split(" ")[1];
            }
        }
        throw new RuntimeException();
    }


}
