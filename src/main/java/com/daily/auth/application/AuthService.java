package com.daily.auth.application;

import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserJoinRequest;
import com.daily.domain.user.dto.UserLoginResponse;
import com.daily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserLoginResponse join(final UserJoinRequest joinRequest) {
        User isUser = userRepository.findById(joinRequest.getEmail()).orElse(null);

        if (isUser != null)
            throw new DuplicateKeyException("이미 사용 중인 이메일입니다.");

        User user = userRepository.save(joinRequest.toUser());

        return new UserLoginResponse(user);
    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        return user;
    }
}
