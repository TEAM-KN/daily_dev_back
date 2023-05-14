package com.daily.domain.contents.repository;

import com.daily.domain.contents.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

}
