package com.daily.domain.site.repository;

import com.daily.domain.site.domain.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteRepository extends JpaRepository<Site, String> {

    boolean existsAllBySiteCodeIn(List<String> siteCodes);

    List<Site> findAllByBatchYn(String yn);
}
