package com.dlog.domain.user.domain;

import com.dlog.global.domain.BaseEntity;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name="image_url", nullable = false)
    private String imageUrl;

    @Column(name="subscribe_yn")
    private String subscribeYn;

    protected User() {

    }

    public User(final String email, final String nickname, final String imageUrl, final String subscribeYn) {
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.subscribeYn = subscribeYn;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSubscribeYn() {
        return subscribeYn;
    }
}
