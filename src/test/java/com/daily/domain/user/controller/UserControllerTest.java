package com.daily.domain.user.controller;

import com.daily.common.ControllerTest;
import com.daily.domain.site.exception.NoSearchSiteException;
import com.daily.domain.user.exception.NoSearchUserException;
import com.daily.fixtures.CommonFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.daily.fixtures.UserFixtures.USER_REQUEST;
import static com.daily.fixtures.UserFixtures.userResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTest {

    @DisplayName("회원 정보를 조회한다")
    @Test
    void 회원_정보를_조회한다() throws Exception {
        // given
        given(userService.fetchUser(any())).willReturn(userResponse);

        // when
        MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("email", "scnoh0617@gmail.com");

        // then
        mockMvc.perform(get("/user")
                .params(queryParam))
                .andDo(print())
                .andDo(document("user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("별명"),
                                fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("프로필 사진 경로"),
                                fieldWithPath("subscribeYn").type(JsonFieldType.STRING).description("구독 정보")
                        )))
                .andExpect(status().isOk());
    }

    @DisplayName("존재하지 않는 사용자 조회를 하면 예외를 발생한다")
    @Test
    void 존재하지_않는_사용자_회원_조회를_하면_예외를_발생한다() throws Exception {
        // given
        given(userService.fetchUser(any())).willThrow(new NoSearchUserException());

        // when
        MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("email", "scnoh0617@gmail.com");

        // then
        mockMvc.perform(get("/user")
                .params(queryParam))
                .andDo(print())
                .andDo(document("user not found",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
                        )))
                .andExpect(status().isNotFound());
    }

    @DisplayName("사용자별 구독 사이트 정보를 업데이트 한다")
    @Test
    void 사용자별_구독_사이트_정보를_업데이트_한다() throws Exception{
        // given
        given(userService.saveUserSites(any())).willReturn(CommonFixtures.COMMON_RESPONSE(200, "성공"));

        // when & then
        mockMvc.perform(post("/user/sites")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(USER_REQUEST()))
                )
                .andDo(print())
                .andDo(document("user site update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("siteCodes").type(JsonFieldType.ARRAY).description("사이트 코드")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
                        )))
                .andExpect(status().isOk());

    }

    @DisplayName("존재하지 않는 사이트 정보를 업데이트 할때 예외를 발생한다")
    @Test
    void 존재하지_않는_사이트_정보를_업데이트_할때_예외를_발생한다() throws Exception{
        // given
        given(userService.saveUserSites(any())).willThrow(new NoSearchSiteException());

        // when & then
        mockMvc.perform(post("/user/sites")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_REQUEST()))
                )
                .andDo(print())
                .andDo(document("user site update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("siteCodes").type(JsonFieldType.ARRAY).description("사이트 코드")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지")
                        )))
                .andExpect(status().isNotFound());

    }

}