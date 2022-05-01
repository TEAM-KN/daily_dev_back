package com.news.dev.api.contents.repository;

import com.news.dev.api.contents.entity.ContentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsRepository extends JpaRepository<ContentsEntity, Integer> {

}
