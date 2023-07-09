package com.daily.auth.controller;

import com.daily.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.daily.fixtures.CommonFixtures.COMMON_RESPONSE;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerTest {

    @DisplayName("사용자 중복 검증 성공")
    @Test
    void isUserCheckSuccess() throws Exception {
        // given
        given(authService.isCheck(anyString())).willReturn(COMMON_RESPONSE());

        // when
        MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("email", "scnoh0617@gmail.com");

        // then
        mockMvc.perform(get("/auth/isCheck")
                .params(queryParam))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.message", is("성공")))
                .andDo(print());
    }

    @DisplayName("사용자 중복 검증 실패")
    @Test
    void isUserCheckFailed() throws Exception {
        // given
        given(authService.isCheck(anyString())).willReturn(COMMON_RESPONSE(409, "이미 사용 중인 이메일입니다."));

        // when
        MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("email", "scnoh0617@gmail.com");

        // then
        mockMvc.perform(get("/auth/isCheck")
                .params(queryParam))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(409)))
                .andExpect(jsonPath("$.message", is("이미 사용 중인 이메일입니다.")))
                .andDo(print());
    }
}