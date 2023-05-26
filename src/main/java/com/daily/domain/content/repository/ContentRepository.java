package com.daily.domain.content.repository;

import com.daily.domain.content.domain.Content;
import com.daily.domain.content.domain.ContentPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, ContentPK> {

}
