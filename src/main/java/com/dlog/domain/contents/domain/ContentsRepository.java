package com.dlog.domain.contents.domain;

import com.dlog.domain.contents.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

    Collection<Contents> findAllByUpdDtm(LocalDate now);

}
