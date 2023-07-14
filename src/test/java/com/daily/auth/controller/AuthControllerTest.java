package com.daily.auth.controller;

import com.daily.auth.exception.ExistUserException;
import com.daily.auth.exception.PasswordMatchException;
import com.daily.common.ControllerTest;
import com.daily.domain.user.exception.NoSearchUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.daily.fixtures.AuthFixtures.*;
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

    @DisplayName("로그인 실패 - 패스워드 불일치")
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
                .andDo(document("password miss match",
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

    @DisplayName("로그인 실패 - 사용자 찾을 수 없음")
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
                .andDo(document("user not found",
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

    @DisplayName("회원가입 성공")
    @Test
    void joinSuccess() throws Exception {
        // given
        given(authService.join(any(), any())).willReturn(COMMON_RESPONSE(200, "회원가입 성공"));

        // when & then
        mockMvc.perform(post("/auth/join")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(JOIN_REQUEST())))
                .andDo(print())
                .andDo(document("join",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 ID"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("사용자 별명"),
                                fieldWithPath("siteCodes").type(JsonFieldType.ARRAY).description("구독 리스트")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
                        )
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("회원가입 실패 - 이미 사용 중인 이메일")
    @Test
    void joinEmailDuplicateFailed() throws Exception {
        // given
        given(authService.join(any(), any())).willThrow(new ExistUserException());

        // when & then
        mockMvc.perform(post("/auth/join")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(JOIN_REQUEST())))
                .andDo(print())
                .andDo(document("join",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 ID"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("사용자 별명"),
                                fieldWithPath("siteCodes").type(JsonFieldType.ARRAY).description("구독 리스트")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
                        )
                ))
                .andExpect(status().isConflict());
    }
}