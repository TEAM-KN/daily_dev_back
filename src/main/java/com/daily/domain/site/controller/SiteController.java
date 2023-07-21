package com.daily.domain.site.controller;

import com.daily.domain.site.application.SiteService;
import com.daily.domain.site.dto.SiteDto;
import com.daily.domain.site.dto.SiteParam;
import com.daily.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SiteController {

    private final SiteService siteService;

    @GetMapping("/sites")
    public List<SiteDto> fetchSites() {
        return siteService.fetchSites();
    }

    @GetMapping("/site")
    public SiteDto fetchSite(@RequestParam @NotBlank(message = "사이트 코드는 필수 값 입니다.") final String siteCode) {
        return siteService.fetchSite(siteCode);
    }

    @PostMapping("/site")
    public CommonResponse saveSite(@RequestBody @Valid final SiteParam siteParam) {
        return siteService.saveSite(siteParam);
    }

}
