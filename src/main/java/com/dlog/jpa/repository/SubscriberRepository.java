package com.dlog.jpa.repository;

import com.dlog.api.subscriber.dto.SubscriberDto;
import com.dlog.jpa.entity.QUserEntity;
import com.dlog.jpa.entity.UserEntity;
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
