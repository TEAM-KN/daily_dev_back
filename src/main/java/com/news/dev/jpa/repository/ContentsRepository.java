package com.news.dev.jpa.repository;

import com.news.dev.jpa.entity.ContentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentsRepository extends JpaRepository<ContentsEntity, Integer> {

}
