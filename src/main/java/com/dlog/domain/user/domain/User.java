package com.dlog.domain.user.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_NO")
    private Long userNo;

    @Column(name="USERNAME", length = 250, unique = true)
    private String username;

    @Column(name="NICKNAME", length = 250, unique = true)
    private String nickname;

    @Column(name="PASSWORD", unique = true)
    private String password;

    @Column(name="JOIN_DTM")
    @CreationTimestamp
    private LocalDateTime joinDtm;

    @Column(name="SUBSCRIBE_YN")
//    @ColumnDefault("N")
    private String subscribeYn;

    @Column(name="TOKEN")
    private String token;
}
