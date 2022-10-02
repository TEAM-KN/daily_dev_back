package com.dlog.auth.user.service;

import com.dlog.auth.user.dto.UserDto;
import com.dlog.auth.user.dto.UserJoinRequest;
import com.dlog.auth.user.dto.UserLoginRequest;
import com.dlog.auth.user.dto.UserLoginResponse;
import com.dlog.jpa.entity.UserEntity;
import com.dlog.jpa.repository.UserRepository;
import com.dlog.util.JwtTokenUtil;
import com.news.dev.auth.user.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
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
        String accessToken = jwtTokenUtil.createToken(userEntity.getUsername());
        String refreshToken = jwtTokenUtil.createRefreshToken();

        userEntity.setToken(refreshToken);
        userRepository.save(userEntity);

        UserLoginResponse loginRs = new ModelMapper().map(userEntity, UserLoginResponse.class);
        loginRs.setAccessToken(accessToken);
        loginRs.setRefreshToken(refreshToken);

        return loginRs;
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public UserDto getUserByUsername(String username) throws Exception {
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        UserDto resultUser = new ModelMapper().map(userEntity, UserDto.class);
        return resultUser;
    }

    @Override
    public UserDto refresh(UserDto user) throws Exception {
        /* 구독자 조회 */
        UserEntity refresh = userRepository.findByUserNoAndToken(user.getUserNo(), user.getRefreshToken());

        if(refresh.getUserNo() == null && refresh.getToken() == null) {
            throw new IllegalArgumentException();
        }

        String accessToken = jwtTokenUtil.createToken(refresh.getUsername());

        UserDto refreshUser = new UserDto();
        refreshUser.setUserNo(refresh.getUserNo());
        refreshUser.setUsername(refresh.getUsername());
        refreshUser.setNickname(refresh.getNickname());
        refreshUser.setPwd(refresh.getPassword());
        refreshUser.setSubscribeYn(refresh.getSubscribeYn());
        refreshUser.setAccessToken(accessToken);
        refreshUser.setRefreshToken(refresh.getToken());

        return refreshUser;
    }
}
