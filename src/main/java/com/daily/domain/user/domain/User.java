package com.daily.domain.user.domain;

import com.daily.global.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="email")
    private String email;

    @Column(name="nickname")
    private String nickname;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="subscribe_yn")
    private String subscribeYn;

}
