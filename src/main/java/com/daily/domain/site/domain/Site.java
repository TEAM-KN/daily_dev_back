package com.daily.domain.site.domain;

import com.daily.comn.domain.BaseEntity;
import com.daily.domain.contents.domain.Contents;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Site extends BaseEntity {

    @Id
    @Column(name = "site_code")
    private String siteCode;

    @Column(name = "site_name")
    private String siteName;

    @Column(name= "site_desc")
    private String siteDesc;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "siteCode")
    List<Contents> contents;

}