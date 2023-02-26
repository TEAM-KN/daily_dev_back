package com.dlog.domain.contents.repository;

import com.dlog.domain.contents.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

}
