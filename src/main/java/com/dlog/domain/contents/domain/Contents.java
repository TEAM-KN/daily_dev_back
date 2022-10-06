package com.dlog.domain.contents.domain;

import com.dlog.global.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="contents")
public class Contents extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="link")
    private String link;

    @Column(name="reg_dtm")
    private String regDtm;

    @Column(name="author")
    private String author;

    @Column(name="upd_dtm")
    @CreationTimestamp
    private LocalDateTime updDtm;

    @Column(name="content_type")
    private String contentType;

    @Column(name="company_cd")
    private String companyCd;

    @Column(name="company_nm")
    private String companyNm;
}
