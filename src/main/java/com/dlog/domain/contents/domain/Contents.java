package com.dlog.domain.contents.domain;

import com.dlog.global.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
<<<<<<< HEAD:src/main/java/com/dlog/domain/contents/domain/Contents.java
@Table(name="contents")
public class Contents extends BaseEntity {
=======
@Table(name="TB_CONTENTS")
//@NoArgsConstructor
//@AllArgsConstructor
public class Contents {
>>>>>>> main:src/main/java/com/dlog/jpa/entity/ContentsEntity.java
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
