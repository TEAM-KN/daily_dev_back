package com.daily.auth.application;

import com.daily.user.domain.User;
import com.daily.user.dto.UserJoinRequest;
import com.daily.user.dto.UserLoginRequest;
import com.daily.user.dto.UserLoginResponse;
import com.daily.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public UserLoginResponse join(UserJoinRequest joinRq) {
        User user = userRepository.save(User.builder()
                .email(joinRq.getEmail())
                .nickname(joinRq.getNickname())
                .subscribeYn(joinRq.getSubscribeYn())
                .imageUrl(joinRq.getImageUrl())
                .password(joinRq.getPassword())
                .build());

        return new UserLoginResponse(user);
    }

    public UserLoginResponse login(UserLoginRequest loginRq) {
        User user = userRepository.findByEmail(loginRq.getUsername());

        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        return new UserLoginResponse(user);
    }

}
