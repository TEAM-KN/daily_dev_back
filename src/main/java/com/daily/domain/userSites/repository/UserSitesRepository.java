package com.daily.domain.userSites.repository;

import com.daily.domain.userSites.domain.UserSites;
import com.daily.domain.userSites.domain.UserSitesPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSitesRepository extends JpaRepository<UserSites, UserSitesPK> {

}
