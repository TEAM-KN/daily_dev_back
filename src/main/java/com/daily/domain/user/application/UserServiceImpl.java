package com.daily.domain.user.application;

import com.daily.domain.user.domain.User;
import com.daily.domain.user.repository.UserRepository;
import com.daily.domain.user.dto.UserDto;
import com.daily.domain.user.dto.UserJoinRequest;
import com.daily.domain.user.dto.UserLoginRequest;
import com.daily.domain.user.dto.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserLoginResponse join(UserJoinRequest joinRq) {
        User user = userRepository.save(User.builder()
                .email(joinRq.getEmail())
                .nickname(joinRq.getNickname())
                .subscribeYn(joinRq.getSubscribeYn())
                .imageUrl(joinRq.getImageUrl())
                .password(joinRq.getPassword())
                .build());

        return UserLoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .subscribeYn(user.getSubscribeYn())
                .imageUrl(user.getImageUrl())
                .build();
    }

    @Override
    public UserLoginResponse login(UserLoginRequest loginRq) {
        User user = userRepository.findByEmail(loginRq.getUsername());

        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        return UserLoginResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .subscribeYn(user.getSubscribeYn())
                .imageUrl(user.getImageUrl())
                .build();
    }

    @Override
    public UserDto getUserByUsername(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        UserDto resultUser = new ModelMapper().map(user, UserDto.class);
        return resultUser;
    }

    @Override
    public UserDto refresh(UserDto user) {

        UserDto refreshUser = new UserDto();

        return refreshUser;
    }
}
