package com.daily.domain.user.controller;

import com.daily.common.ControllerTest;
import com.daily.fixtures.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.daily.fixtures.UserFixtures.userResponse;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTest {

    @DisplayName("사용자 조회")
    @Test
    void fetchUserTest() throws Exception {
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

}