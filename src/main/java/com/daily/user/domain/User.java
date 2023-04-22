package com.daily.user.domain;

import com.daily.comn.domain.BaseEntity;
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
    private String email;

    private String nickname;

    private String imageUrl;

    private String password;

    private String subscribeYn;

}
