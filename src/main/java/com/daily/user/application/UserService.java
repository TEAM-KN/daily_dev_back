package com.daily.user.application;

import com.daily.user.domain.User;
import com.daily.user.repository.UserRepository;
import com.daily.user.dto.UserDto;
import com.daily.user.dto.UserJoinRequest;
import com.daily.user.dto.UserLoginRequest;
import com.daily.user.dto.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

    public UserDto getUserByUsername(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        UserDto resultUser = new ModelMapper().map(user, UserDto.class);
        return resultUser;
    }
}
