package com.dlog.domain.user.application;

import com.dlog.domain.user.domain.User;
import com.dlog.domain.user.dto.UserDto;
import com.dlog.domain.user.dto.UserJoinRequest;
import com.dlog.domain.user.dto.UserLoginRequest;
import com.dlog.domain.user.dto.UserLoginResponse;
import com.dlog.domain.user.repository.UserRepository;
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
                .build());

        UserLoginResponse loginRs = new ModelMapper().map(user, UserLoginResponse.class);

        return loginRs;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest loginRq) {
        User user = userRepository.findByEmail(loginRq.getUsername());

        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        userRepository.save(user);

        UserLoginResponse loginRs = new ModelMapper().map(user, UserLoginResponse.class);

        return loginRs;
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByEmail(username);

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
