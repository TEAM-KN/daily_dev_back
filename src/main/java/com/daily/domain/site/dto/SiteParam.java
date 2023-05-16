package com.daily.domain.site.dto;

import com.daily.domain.site.domain.Site;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteParam {

    @NotBlank(message = "Site code can not be blank")
    private String siteCode;

    @NotBlank(message = "Site name can not be blank")
    private String siteName;

    private String siteDesc;

    public Site toSite() {
        return Site.builder()
                .siteCode(this.siteCode)
                .siteName(this.siteName)
                .siteDesc(this.siteDesc)
                .build();
    }

}
