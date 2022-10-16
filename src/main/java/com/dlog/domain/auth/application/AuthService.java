package com.dlog.domain.auth.application;

import com.dlog.domain.user.domain.User;
import com.dlog.domain.user.domain.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(final String email) {
        if (userRepository.existsByEmail(email)) {
            return userRepository.findByEmail(email);
        } else {
            throw new UsernameNotFoundException("사용자의 정보를 찾을 수 없습니다.");
        }
    }


}
