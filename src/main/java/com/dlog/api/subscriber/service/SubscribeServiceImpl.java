package com.dlog.api.subscriber.service;

import com.dlog.jpa.entity.UserEntity;
import com.dlog.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService{

    private final UserRepository userRepository;

    @Override
    public void add(String username) throws Exception {
        UserEntity userEntity = userRepository.findByUsername(username);

        if("N".equals(userEntity.getSubscribeYn())) {
            userEntity.setSubscribeYn("Y");
            userRepository.save(userEntity);
        }
    }

    @Override
    public void cancel(String username) throws Exception {
        UserEntity userEntity = userRepository.findByUsername(username);

        if("Y".equals(userEntity.getSubscribeYn())) {
            userEntity.setSubscribeYn("N");
            userRepository.save(userEntity);
        }
    }
}
