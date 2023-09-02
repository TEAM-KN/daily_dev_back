package com.daily.domain.content.controller;

import com.daily.common.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.daily.fixtures.ContentFixtures.contentDtoList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ContentControllerTest extends ControllerTest {

    @DisplayName("사이트의 컨텐츠 정보를 조회한다")
    @Test
    void 사이트의_컨텐츠_정보를_조회한다() throws Exception {
        // given
        given(contentService.fetchContentsBySiteCode(any())).willReturn(contentDtoList);

        // when
        MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();
        queryParam.add("siteCode", "NAVER");

        // then
        mockMvc.perform(get("/content/site")
                .params(queryParam))
                .andDo(print())
                .andDo(document("content site",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].contentId").type(JsonFieldType.NUMBER).description("컨텐츠 ID"),
                                fieldWithPath("[].siteCode").type(JsonFieldType.STRING).description("사이트 코드"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("컨텐츠 제목"),
                                fieldWithPath("[].description").type(JsonFieldType.STRING).description("컨텐츠 설명"),
                                fieldWithPath("[].link").type(JsonFieldType.STRING).description("컨텐츠 링크"),
                                fieldWithPath("[].regDtm").type(JsonFieldType.STRING).description("작성 일자"),
                                fieldWithPath("[].author").type(JsonFieldType.STRING).description("작성자")
                        )))
                .andExpect(status().isOk());
    }
}