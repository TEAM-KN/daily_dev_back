package com.daily.domain.content.repository;

import com.daily.domain.content.domain.Content;
import com.daily.domain.content.domain.ContentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ContentRepository extends JpaRepository<Content, ContentPK> {

    @Query(value = "SELECT c " +
            "FROM Content c " +
            "WHERE c.siteCode IN :siteCodes " +
            "   AND c.createDate >= :start " +
            "   AND c.createDate <= :end")
    List<Content> fetchContentBatchQuery(String[] siteCodes, LocalDateTime start, LocalDateTime end);
    @Query(value = "SELECT c FROM Content c WHERE c.createDate <= :removeDate AND c.siteCode = :siteCode")
    List<Content> fetchRemoveNaverContentBatchQuery(LocalDateTime removeDate, String siteCode);
    List<Content> findAllByOrderByRegDtmDesc();
    List<Content> findAllBySiteCodeOrderByCreateDateDesc(String siteCode);

}
