package com.daily.domain.content.repository;

import com.daily.domain.content.domain.Content;
import com.daily.domain.content.domain.ContentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ContentRepository extends JpaRepository<Content, ContentPK> {

    @Query(value = "SELECT c " +
            "FROM Content c " +
            "WHERE c.siteCode = :siteCode " +
            "   AND c.createDate >= :start " +
            "   AND c.createDate <= :end")
    Content fetchContentBatchQuery(String siteCode, LocalDateTime start, LocalDateTime end);
}
