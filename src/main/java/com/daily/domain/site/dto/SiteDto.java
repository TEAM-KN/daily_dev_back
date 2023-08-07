package com.daily.domain.site.dto;

import com.daily.domain.content.domain.Content;
import com.daily.domain.site.domain.Site;
import com.daily.domain.userSites.domain.UserSites;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SiteDto {

    private String siteCode;
    private String siteName;
    private String siteDesc;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public SiteDto(final Site site) {
        this.siteCode = site.getSiteCode();
        this.siteName = site.getSiteName();
        this.siteDesc = site.getSiteDesc();
        this.createDate = site.getCreateDate();
        this.updateDate = site.getUpdateDate();
    }

    public SiteDto(final UserSites userSites) {
        this.siteCode = userSites.getSiteCode();
        this.siteName = userSites.getSite().getSiteName();
        this.siteDesc = userSites.getSite().getSiteDesc();
        this.createDate = userSites.getSite().getCreateDate();
        this.updateDate = userSites.getSite().getUpdateDate();
    }

    @Data
    public static class SiteWithContentsDto extends SiteDto {
        private List<Content> contents;

        public SiteWithContentsDto(final Site site) {
            super(site);
            this.contents = site.getContents();
        }
    }

}
