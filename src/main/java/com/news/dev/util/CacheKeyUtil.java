package com.news.dev.util;

import org.springframework.stereotype.Component;

@Component
public class CacheKeyUtil {

    public final int DEFAULT_EXPIRE_SECOND = 60; // 1분
    public final int USER_EXPIRE_SECOND = 60 * 5; // 5분

}
