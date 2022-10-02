package com.dlog.jpa.repository;

import com.dlog.jpa.entity.ContentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;

public interface ContentsRepository extends JpaRepository<ContentsEntity, Long> {

    Collection<ContentsEntity> findAllByUpdDtm(LocalDate now);

}
