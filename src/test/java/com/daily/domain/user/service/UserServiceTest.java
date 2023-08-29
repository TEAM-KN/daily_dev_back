package com.daily.domain.user.service;

import com.daily.common.ServiceTest;
import com.daily.domain.user.application.UserService;
import com.daily.domain.user.domain.User;
import com.daily.domain.user.dto.UserDto;
import com.daily.domain.user.dto.UserRequest;
import com.daily.global.common.response.CommonResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static com.daily.fixtures.UserFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


public class UserServiceTest extends ServiceTest {

    @InjectMocks
    private UserService userService;

    @DisplayName("회원 정보를 정상적으로 조회한다.")
    @Test
    void fetchUser() {
        // given & when
        given(userRepository.fetchUserWithSite(any(String.class))).willReturn(Optional.of(USER()));

        // then
        UserDto.UserWithSite userWithSite = new UserDto.UserWithSite(USER());
        assertThat(userService.fetchUser(email)).usingRecursiveComparison().isEqualTo(userWithSite);

    }

    @DisplayName("회원 정보를 정상적으로 업데이트 한다.")
    @Test
    void saveUser() {
        // given
        UserRequest.Update param = USER_UPDATE_REQUEST();

        // when
        given(userRepository.updateUserByNickname(any(String.class), any(String.class))).willReturn(USER());

        // then
        CommonResponse response = new CommonResponse(HttpStatus.OK, "성공");
        assertThat(userService.saveUser(param)).usingRecursiveComparison().isEqualTo(response);

    }

}
