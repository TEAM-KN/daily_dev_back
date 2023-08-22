package com.daily.domain.site.domain;

import com.daily.domain.userSites.domain.UserSites;
import com.daily.global.common.domain.BaseEntity;
import com.daily.domain.content.domain.Content;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "site")
public class Site extends BaseEntity {

    @Id
    @Column(name = "site_code")
    private String siteCode;

    @Column(name = "site_name")
    private String siteName;

    @Column(name= "site_desc")
    private String siteDesc;

    @Column(name = "batch_yn")
    private String batchYn;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "site")
    List<UserSites> userSites;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "siteCode")
    List<Content> contents;

}
