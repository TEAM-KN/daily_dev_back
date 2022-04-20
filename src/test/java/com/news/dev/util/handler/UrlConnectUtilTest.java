package com.news.dev.util.handler;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UrlConnectUtilTest {

    private final UrlConnectUtil urlConnectUtil;

    @DisplayName("Get Html to Url is")
        @ParameterizedTest
        @CsvSource({"https://www.naver.com, <title>NAVER</title>", "https://www.google.com, <title>GOOGLE</title>"})
        void urlSuccessTest(final String url, final String title) {
            final String result = urlConnectUtil.getHtml("https://www.naver.com");

            Assertions.assertThat(result.contains("<title>NAVER</title>")).isTrue();
        }

        @DisplayName("Get Html to Url is")
        @Test
        void urlFailTest() {
            Assertions.assertThatThrownBy(() -> urlConnectUtil.getHtml("wrong.url.ccc"))
                    .isInstanceOf(IllegalArgumentException.class);
        }

}