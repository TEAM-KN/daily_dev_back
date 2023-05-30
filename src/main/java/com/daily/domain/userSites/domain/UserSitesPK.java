package com.daily.domain.userSites.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSitesPK implements Serializable {

    private String email;

    private String siteCode;

}
