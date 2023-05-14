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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long siteId;

    private String siteName;

    private String siteCode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contents")
    List<Contents> contents;

}
