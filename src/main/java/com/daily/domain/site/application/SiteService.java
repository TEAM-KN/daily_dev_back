package com.daily.domain.site.application;

import com.daily.domain.site.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;

}
