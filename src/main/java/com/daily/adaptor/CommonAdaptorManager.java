package com.daily.adaptor;

import com.daily.domain.content.domain.Content;
import com.daily.domain.site.domain.Site;
import com.daily.domain.site.repository.SiteRepository;
import com.daily.global.common.dto.YN;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.daily.domain.content.dto.ContentType.*;

@Component
@RequiredArgsConstructor
@Transactional
public class CommonAdaptorManager {

    private final SiteRepository siteRepository;
    private final KakaoAdaptor kakaoAdaptor;
    private final WoowahanAdaptor woowahanAdaptor;
    private final NaverNewsAdaptor naverNewsAdaptor;
    private final DaangnAdaptor daangnAdaptor;
    private final LineAdaptor lineAdaptor;
    private final GmarketAdaptor gmarketAdaptor;

    public List<Content> of(String requestDate) {
        List<Content> contents = new ArrayList<>();
        List<Site> sites = siteRepository.findAllByBatchYn(YN.Y.name());

        for (Site site : sites) {
            CommonAdaptor adaptor = this.getAdaptorForSite(site.getSiteCode());
            if (adaptor != null) {
                List<Content> newContents = adaptor.getNewContentsFromAdaptor(requestDate);
                contents.addAll(newContents);
            }
        }

        return contents;
    }

    private CommonAdaptor getAdaptorForSite(final String siteCode) {
        if (WOO.name().equals(siteCode))
            return woowahanAdaptor;
        else if (KAKAO.name().equals(siteCode))
            return kakaoAdaptor;
        else if (NAVER.name().equals(siteCode))
            return naverNewsAdaptor;
        else if (DAANGN.name().equals(siteCode))
            return daangnAdaptor;
        else if (LINE.name().equals(siteCode))
            return lineAdaptor;
        else if (GMARKET.name().equals(siteCode))
            return gmarketAdaptor;
        return null;
    }
}
