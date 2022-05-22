package com.news.dev.config.security;

import com.news.dev.jpa.entity.UserEntity;
import com.news.dev.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity == null) { //사용자가 존재하지 않을 때
            throw new UsernameNotFoundException(username);
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(),
                true, true, true, true,
                new ArrayList<>());
    }
}
