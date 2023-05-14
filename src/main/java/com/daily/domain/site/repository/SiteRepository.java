package com.daily.domain.site.repository;

import com.daily.domain.site.domain.Site;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<Site, Long> {
}
