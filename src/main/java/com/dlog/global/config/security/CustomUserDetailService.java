package com.dlog.global.config.security;

import com.dlog.domain.user.domain.User;
import com.dlog.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
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
<<<<<<< HEAD:src/main/java/com/dlog/global/config/security/CustomUserDetailService.java
        User user = userRepository.findByEmail(username);
=======
        User user = userRepository.findByUsername(username);
>>>>>>> main:src/main/java/com/dlog/config/security/CustomUserDetailService.java

        if(user == null) { //사용자가 존재하지 않을 때
            throw new UsernameNotFoundException(username);
        }

<<<<<<< HEAD:src/main/java/com/dlog/global/config/security/CustomUserDetailService.java
        return new org.springframework.security.core.userdetails.User(user.getEmail(), "",
=======
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
>>>>>>> main:src/main/java/com/dlog/config/security/CustomUserDetailService.java
                true, true, true, true,
                new ArrayList<>());
    }
}
