package com.daily.domain.userSites.domain;

import com.daily.domain.site.domain.Site;
import com.daily.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserSitesPK.class)
@Table(name = "user_sites")
public class UserSites {

    @Id
    @Column(name = "email")
    private String email;

    @Id
    @Column(name = "site_code")
    private String siteCode;

    @ManyToOne
    @JoinColumn(name = "email", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "site_code", insertable = false, updatable = false)
    private Site site;

}
