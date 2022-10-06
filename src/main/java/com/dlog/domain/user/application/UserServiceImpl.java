package com.dlog.domain.user.application;

import com.dlog.domain.user.domain.User;
import com.dlog.domain.user.dto.UserDto;
import com.dlog.domain.user.dto.UserJoinRequest;
import com.dlog.domain.user.dto.UserLoginRequest;
import com.dlog.domain.user.dto.UserLoginResponse;
import com.dlog.domain.user.domain.UserRepository;
import com.dlog.domain.comn.JwtTokenUtil;
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
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserLoginResponse join(UserJoinRequest joinRq) throws Exception {
        User entity = new ModelMapper().map(joinRq,  User.class);

        User rsEntity = userRepository.save(entity);

        UserLoginResponse loginRs = new ModelMapper().map(rsEntity, UserLoginResponse.class);

        return loginRs;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest loginRq) throws Exception {
        User user = userRepository.findByEmail(loginRq.getUsername());

        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        String accessToken = jwtTokenUtil.createToken(user.getEmail());
        String refreshToken = jwtTokenUtil.createRefreshToken();

//        user.setToken(refreshToken);
        userRepository.save(user);

        UserLoginResponse loginRs = new ModelMapper().map(user, UserLoginResponse.class);
        loginRs.setAccessToken(accessToken);
        loginRs.setRefreshToken(refreshToken);

        return loginRs;
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public UserDto getUserByUsername(String username) throws Exception {
        User user = userRepository.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }

        UserDto resultUser = new ModelMapper().map(user, UserDto.class);
        return resultUser;
    }

    @Override
    public UserDto refresh(UserDto user) throws Exception {
        /* 구독자 조회 */
        User refresh = userRepository.findByUserNoAndToken(user.getUserNo(), user.getRefreshToken());

//        if(refresh.getUserNo() == null && refresh.getToken() == null) {
//            throw new IllegalArgumentException();
//        }

        String accessToken = jwtTokenUtil.createToken(refresh.getEmail());

        UserDto refreshUser = new UserDto();
        refreshUser.setUserNo(refresh.getId());
        refreshUser.setUsername(refresh.getEmail());
        refreshUser.setNickname(refresh.getNickname());
        refreshUser.setSubscribeYn(refresh.getSubscribeYn());
        refreshUser.setAccessToken(accessToken);

        return refreshUser;
    }
}
