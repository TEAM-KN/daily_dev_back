package com.news.dev.api.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "TB_USERS")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userNo;

    @Column(length = 250, unique = true)
    private String email;

    @Column(length = 250, unique = true)
    private String nickname;

    @Column(unique = true)
    private String password;
}
