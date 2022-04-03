package com.news.dev.api.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TB_USERS")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_NO")
    private Long userNo;

    @Column(name="EMAIL", length = 250, unique = true)
    private String email;

    @Column(name="NICKNAME", length = 250, unique = true)
    private String nickname;

    @Column(name="PASSWORD", unique = true)
    private String password;

    @Column(name="JOIN_DTM")
    private LocalDateTime joinDtm;
}
