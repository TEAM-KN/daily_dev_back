package com.news.dev.jpa.repository;

import com.news.dev.api.subscriber.dto.SubscriberDto;
import com.news.dev.jpa.entity.QUserEntity;
import com.news.dev.jpa.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscriberRepository {

    private final JPAQueryFactory query;

    public List<SubscriberDto> findSubscribers() {
        QUserEntity qUser = QUserEntity.userEntity;

        List<UserEntity> users = query
                .select(qUser.userEntity)
                .from(qUser.userEntity)
                .where(qUser.userEntity.subscribeYn.eq("Y"))
                .fetch();

        List<SubscriberDto> subscribers = new ArrayList<>();

        for(UserEntity user : users) {
            SubscriberDto subscribe = new SubscriberDto();

            subscribe.setUsername(user.getUsername());
            subscribe.setSubscribeYn(user.getSubscribeYn());

            subscribers.add(subscribe);
        }

        return subscribers;
    }

}
