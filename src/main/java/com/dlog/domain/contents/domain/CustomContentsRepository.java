package com.dlog.domain.contents.domain;

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
    public List<Contents> findNewContents() {
        QContents qContents = QContents.contents;

        LocalDate now = LocalDate.now();

        List<Contents> newContents = query
                .select(qContents.contents)
                .from(qContents.contents)
                .where(qContents.contents.updDtm.gt(now.atStartOfDay()))
                .orderBy(qContents.companyCd.asc())
                .fetch();
        return newContents;

    }

}
