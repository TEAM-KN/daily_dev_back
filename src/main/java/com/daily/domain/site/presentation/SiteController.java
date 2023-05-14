package com.daily.domain.site.presentation;

import com.daily.domain.site.application.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SiteController {

    private SiteService siteService;


}
