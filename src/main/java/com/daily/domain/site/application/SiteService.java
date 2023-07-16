package com.daily.domain.site.application;

import com.daily.domain.site.domain.Site;
import com.daily.domain.site.dto.SiteDto;
import com.daily.domain.site.dto.SiteParam;
import com.daily.domain.site.exception.NoSearchSiteException;
import com.daily.domain.site.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SiteService {

    private final SiteRepository siteRepository;

    @Transactional(readOnly = true)
    public List<SiteDto> fetchSites() {
        List<Site> sites = siteRepository.findAll();
        return sites.stream().map(SiteDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SiteDto fetchSite(final String siteCode) {
        Site site = siteRepository.findById(siteCode).orElseThrow(NoSearchSiteException::new);
        return new SiteDto(site);
    }

    public Site saveSite(final SiteParam siteParam) {
        return siteRepository.save(siteParam.toSite());
    }
}
