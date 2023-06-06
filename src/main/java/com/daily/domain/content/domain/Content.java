package com.daily.domain.content.domain;

import com.daily.global.common.domain.BaseEntity;
import com.daily.domain.site.domain.Site;
import com.daily.global.common.sequence.SequenceGenerator;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="content")
@IdClass(ContentPK.class)
public class Content extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_content")
    @GenericGenerator(name = "seq_content", parameters =
            { @Parameter(name = SequenceGenerator.INCREMENT_PARAM, value = "1") },
            strategy = "com.daily.global.common.sequence.SequenceGenerator"
    )
    @Column(name="content_id")
    private Long contentId;

    @Id
    @Column(name="site_code")
    private String siteCode;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Site site;
}
