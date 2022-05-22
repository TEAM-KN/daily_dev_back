package com.news.dev.jpa.repository;

import com.news.dev.jpa.entity.ContentsEntity;
import com.news.dev.api.contents.entity.QContentsEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomContentsRepository {

    private final JPAQueryFactory query;

    public List<ContentsEntity> findNewContents() {
        QContentsEntity qContents = QContentsEntity.contentsEntity;

        LocalDate now = LocalDate.now();

        List<ContentsEntity> newContents = query
                .select(qContents.contentsEntity)
                .from(qContents.contentsEntity)
                .where(qContents.contentsEntity.updDtm.gt(now.atStartOfDay()))
                .fetch();
        return newContents;

    }

}
