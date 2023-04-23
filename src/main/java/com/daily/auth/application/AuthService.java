package com.daily.auth.application;

import com.daily.user.domain.User;
import com.daily.user.dto.UserJoinRequest;
import com.daily.user.dto.UserLoginRequest;
import com.daily.user.dto.UserLoginResponse;
import com.daily.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;

    public UserLoginResponse join(UserJoinRequest joinRequest) {
        User isUser = userRepository.findById(joinRequest.getEmail()).orElse(null);

        if (isUser != null)
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");

        User user = userRepository.save(joinRequest.toUser());

        return new UserLoginResponse(user);
    }

    @Transactional(readOnly = true)
    public UserLoginResponse login(UserLoginRequest loginRequest) {
        User user = userRepository.findById(loginRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("Not found User"));
        return new UserLoginResponse(user);
    }

}
