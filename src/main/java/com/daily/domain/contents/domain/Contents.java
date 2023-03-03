package com.daily.domain.contents.domain;

import com.daily.global.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="contents")
public class Contents extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="contents_id")
    private Long contentsId;

    @Column(name="link")
    private String link;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="reg_dtm")
    private String regDtm;

    @Column(name="author")
    private String author;

    @Column(name="content_type")
    private String contentType;

    @Column(name="company_cd")
    private String companyCd;

    @Column(name="company_nm")
    private String companyNm;
}
