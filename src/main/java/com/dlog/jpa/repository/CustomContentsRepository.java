package com.dlog.jpa.repository;

import com.dlog.jpa.entity.ContentsEntity;
import com.dlog.jpa.entity.QContentsEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomContentsRepository {

    private final JPAQueryFactory query;

    @Transactional(readOnly = true)
    public List<ContentsEntity> findNewContents() {
        QContentsEntity qContents = QContentsEntity.contentsEntity;

        LocalDate now = LocalDate.now();

        List<ContentsEntity> newContents = query
                .select(qContents.contentsEntity)
                .from(qContents.contentsEntity)
                .where(qContents.contentsEntity.updDtm.gt(now.atStartOfDay()))
                .orderBy(qContents.companyCd.asc())
                .fetch();
        return newContents;

    }

}
