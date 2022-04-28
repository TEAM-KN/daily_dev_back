package com.news.dev.api.contents.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="TB_CONTENTS")
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

    @Column(name="REG_DATE")
    private String regDate;

    @Column
    private String author;
}
