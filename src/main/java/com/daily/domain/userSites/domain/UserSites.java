package com.daily.domain.userSites.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserSitesPK.class)
public class UserSites {

    @Id
    private String email;

    @Id
    private String siteCode;

}
