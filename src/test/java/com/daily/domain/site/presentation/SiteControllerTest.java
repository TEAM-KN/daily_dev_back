package com.daily.domain.site.presentation;

import com.daily.common.ControllerTest;
import com.daily.domain.site.domain.Site;
import com.daily.domain.site.dto.SiteDto;
import com.daily.domain.site.exception.NoSearchSiteException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

import static com.daily.fixtures.SiteFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SiteControllerTest extends ControllerTest {

    @DisplayName("사이트 정보 전체 조회")
    @Test
    void getSitesTest() throws Exception {
        // given
        List<Site> sites = List.of(WOO_SITE(), NAVER_SITE(), KAKAO_SITE());
        List<SiteDto> siteResponse = sites.stream().map(SiteDto::new).collect(Collectors.toList());
        given(siteService.fetchSites()).willReturn(siteResponse);

        // when & then
        mockMvc.perform(get("/sites")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("sites",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                    )
                )
                .andExpect(status().isOk());

    }

    @DisplayName("사이트 코드에 따른 사이트 정보를 조회")
    @Test
    void getSiteBySiteCodeTest() throws Exception {
        // given
        Site site = WOO_SITE();
        SiteDto siteResponse = new SiteDto(site);
        given(siteService.fetchSite(any())).willReturn(siteResponse);

        // when & then
        mockMvc.perform(get("/site?siteCode={siteCode}", "WOO")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("site",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("siteCode").description("사이트 코드")
                        )
                    )
                )
                .andExpect(status().isOk());
    }

    @DisplayName("사이트 정보가 없으면 404 에러를 반환한다")
    @Test
    void getSiteWithNotFoundTest() throws Exception {
        // given
        given(siteService.fetchSite(any())).willThrow(new NoSearchSiteException());

        // when & then
        mockMvc.perform(get("/site?siteCode={siteCode}", "WOO")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("site not found",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("siteCode").description("사이트 코드")
                        )
                    )
                )
                .andExpect(status().isNotFound());
    }

}