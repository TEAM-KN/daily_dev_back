package com.daily.domain.site.domain;

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
public class Site extends BaseEntity {

    @Id
    @Column(name = "site_code")
    private String siteCode;

    @Column(name = "site_name")
    private String siteName;

    @Column(name= "site_desc")
    private String siteDesc;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "siteCode")
    List<Content> contents;

}
