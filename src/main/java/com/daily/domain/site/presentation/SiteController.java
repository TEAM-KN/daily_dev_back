package com.daily.domain.site.presentation;

import com.daily.comn.exception.dto.ErrorCode;
import com.daily.comn.response.CommonResponse;
import com.daily.domain.site.application.SiteService;
import com.daily.domain.site.dto.SiteDto;
import com.daily.domain.site.dto.SiteParam;
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

    @GetMapping("/site/list")
    public List<SiteDto> fetchSites() {
        return siteService.fetchSites();
    }

    @GetMapping("/site")
    public SiteDto fetchSite(@RequestParam @NotBlank(message = "site can not be blank") String siteCode) {
        return siteService.fetchSite(siteCode);
    }

    @PostMapping("/site")
    public CommonResponse saveSite(@RequestBody @Valid SiteParam siteParam) {
        try {
            siteService.saveSite(siteParam);
            return new CommonResponse(ErrorCode.SUCCESS);
        } catch(Exception e) {
            log.error("error: {}", e.getMessage());
            return new CommonResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
