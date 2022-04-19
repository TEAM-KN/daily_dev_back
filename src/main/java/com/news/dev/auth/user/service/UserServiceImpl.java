package com.news.dev.auth.user.service;

import com.news.dev.auth.user.dto.UserDto;
import com.news.dev.auth.user.dto.UserJoinRequest;
import com.news.dev.auth.user.dto.UserLoginRequest;
import com.news.dev.auth.user.dto.UserLoginResponse;
import com.news.dev.auth.user.entity.UserEntity;
import com.news.dev.auth.user.repository.UserRepository;
import com.news.dev.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserLoginResponse join(UserJoinRequest joinRq) throws Exception {

        UserEntity entity = new ModelMapper().map(joinRq,  UserEntity.class);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        UserEntity rsEntity = userRepository.save(entity);

        UserLoginResponse loginRs = new ModelMapper().map(rsEntity, UserLoginResponse.class);

        return loginRs;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest loginRq) throws Exception {
        UserEntity userEntity = userRepository.findByUsername(loginRq.getUsername());

        if(userEntity == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        UserLoginResponse loginRs = new ModelMapper().map(userEntity, UserLoginResponse.class);

        loginRs.setToken(jwtTokenUtil.createToken(loginRs.getUsername()));

        return loginRs;
    }

    @Override
    public UserDto getUserByUsername(String email) throws Exception {
        UserEntity userEntity = userRepository.findByUsername(email);

        if(userEntity == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        UserDto resultUser = new ModelMapper().map(userEntity, UserDto.class);
        return resultUser;
    }
}
