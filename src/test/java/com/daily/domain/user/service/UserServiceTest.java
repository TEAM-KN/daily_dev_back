package com.daily.domain.user.service;

import com.daily.common.ServiceTest;
import com.daily.domain.user.application.UserService;
import com.daily.domain.user.dto.UserDto;
import com.daily.domain.user.dto.UserRequest;
import com.daily.domain.userSites.domain.UserSites;
import com.daily.global.common.response.CommonResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.daily.fixtures.UserFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class UserServiceTest extends ServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원 정보를 정상적으로 조회한다.")
    @Test
    void fetchUser() {
        // given & when
        given(userRepository.fetchUserWithSite(any(String.class))).willReturn(Optional.of(USER()));

        // then
        UserDto.UserWithSite userWithSite = new UserDto.UserWithSite(USER());
        assertThat(userService.fetchUser(email)).usingRecursiveComparison().isEqualTo(userWithSite);

    }

    @DisplayName("회원 정보를 정상적으로 변경 한다.")
    @Test
    void saveUser() {
        // given
        UserRequest.Update param = USER_UPDATE_REQUEST();

        // when
        given(userRepository.findById(any(String.class))).willReturn(Optional.of(USER()));
        given(userRepository.updateUserByNickname(any(String.class), any(String.class))).willReturn(USER());

        // then
        CommonResponse response = new CommonResponse(HttpStatus.OK, "성공");
        assertThat(userService.saveUser(param)).usingRecursiveComparison().isEqualTo(response);

    }

    @DisplayName("회원의 패스워드를 정상적으로 변경 한다.")
    @Test
    void savePassword() {
        // given
        UserRequest.Password param = new UserRequest.Password(email, password);

        // when
        given(userRepository.findById(any(String.class))).willReturn(Optional.of(USER()));
        given(passwordEncoder.encode(any(String.class))).willReturn("encodePassword");
        given(userRepository.updateUserByPassword(any(String.class), any(String.class))).willReturn(USER());

        // then
        CommonResponse response = new CommonResponse(HttpStatus.OK, "성공");
        assertThat(userService.savePassword(param)).usingRecursiveComparison().isEqualTo(response);

        verify(passwordEncoder, times(1)).encode(any(String.class));
    }

    @DisplayName("회원의 구독 정보를 정상적으로 변경 한다.")
    @Test
    void saveUserSites() {
        // given
        UserRequest.UserFromSiteRequest userFromSiteRequest = USER_REQUEST();

        // when
        given(userRepository.findById(any(String.class))).willReturn(Optional.of(USER()));
        given(siteRepository.existsAllBySiteCodeIn(any(List.class))).willReturn(Boolean.TRUE);

        // then
        CommonResponse response = new CommonResponse(HttpStatus.OK, "성공");
        assertThat(userService.saveUserSites(userFromSiteRequest)).usingRecursiveComparison().isEqualTo(response);

        verify(userSitesRepository, times(1)).deleteAllByEmail(any(String.class));
        verify(userSitesRepository, times(1)).saveAll(any(List.class));
        verify(userRepository, times(1)).updateUserBySubscribeYn(any(String.class), any(String.class));

    }


}
