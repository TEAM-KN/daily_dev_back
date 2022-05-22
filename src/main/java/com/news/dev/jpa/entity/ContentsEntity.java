package com.news.dev.jpa.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Table(name="TB_CONTENTS")
@NoArgsConstructor
@AllArgsConstructor
public class ContentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CONTENT_NO")
    private Long contentNo;

    @Column(name="TITLE")
    private String title;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="LINK")
    private String link;

    @Column(name="REG_DTM")
    private String regDtm;

    @Column(name="AUTHOR")
    private String author;

    @Column(name="UPD_DTM")
    @CreationTimestamp
    private LocalDateTime updDtm;

    @Column(name="CONTENT_TYPE")
    private String contentType;

    @Column(name="COMPANY_CD")
    private String companyCd;

    @Column(name="COMPANY_NM")
    private String companyNm;
}
