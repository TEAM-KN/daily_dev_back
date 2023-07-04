package com.daily.auth.controller;

import com.daily.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.daily.fixtures.AuthFixtures.USER_RENEW_TOKEN_REQUEST;
import static com.daily.fixtures.AuthFixtures.USER_RENEW_TOKEN_RESPONSE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends ControllerTest {

    @DisplayName("리프레시 토큰으로 새로운 엑세스 토큰을 발급한다.")
    @Test
    void isCheckUser() throws Exception {
        // given
        given(authService.generateAccessToken(any())).willReturn(USER_RENEW_TOKEN_RESPONSE());

        mockMvc.perform(post("/auth/token/access")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(USER_RENEW_TOKEN_REQUEST())))
                .andDo(print())
                .andDo(document("auth/generateAccessToken",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING)
                                        .description("리프레시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING)
                                        .description("새로운 어세스 토큰"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING)
                                        .description("새로운 리프레시 토큰")
                        )))
                .andExpect(status().isOk());
    }
}