package com.daily.auth.controller;

import com.daily.auth.exception.PasswordMatchException;
import com.daily.common.ControllerTest;
import com.daily.domain.user.exception.NoSearchUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.daily.fixtures.AuthFixtures.LOGIN_REQUEST;
import static com.daily.fixtures.AuthFixtures.LOGIN_RESPONSE;
import static com.daily.fixtures.CommonFixtures.COMMON_RESPONSE;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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

    @DisplayName("로그인 성공")
    @Test
    void loginSuccess() throws Exception {
        // given
        given(authService.login(any(), any())).willReturn(LOGIN_RESPONSE(200, "로그인 성공"));

        // when & then
        mockMvc.perform(post("/auth/login")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(LOGIN_REQUEST())))
                .andDo(print())
                .andDo(document("login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 사용자"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("로그인 패스워드")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
                        )
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("패스워드 불일치")
    @Test
    void passwordMissMatch() throws Exception {
        // given
        given(authService.login(any(), any())).willThrow(new PasswordMatchException());

        // when & then
        mockMvc.perform(post("/auth/login")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(LOGIN_REQUEST())))
                .andDo(print())
                .andDo(document("login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 사용자"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("로그인 패스워드")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
                        )
                ))
                .andExpect(status().isNotFound());
    }

    @DisplayName("사용자 찾을 수 없음")
    @Test
    void userNotFound() throws Exception {
        // given
        given(authService.login(any(), any())).willThrow(new NoSearchUserException());

        // when & then
        mockMvc.perform(post("/auth/login")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(LOGIN_REQUEST())))
                .andDo(print())
                .andDo(document("login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("로그인 사용자"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("로그인 패스워드")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
                        )
                ))
                .andExpect(status().isNotFound());
    }
}