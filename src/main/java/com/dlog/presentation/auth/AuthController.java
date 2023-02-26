package com.dlog.presentation.auth;

import com.dlog.domain.user.dto.UserDto;
import com.dlog.domain.user.dto.UserJoinRequest;
import com.dlog.domain.user.dto.UserLoginRequest;
import com.dlog.domain.user.dto.UserLoginResponse;
import com.dlog.domain.user.application.UserService;
import com.dlog.global.exception.ResponseEntityHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class AuthController extends ResponseEntityHandler {

    private final UserService userService;

    @PostConstruct
    public void test() throws Exception {
        userService.join(UserJoinRequest.builder()
                .email("schulnoh@gmail.com")
                .nickname("노짱")
                .subscribeYn("Y")
                .imageUrl("")
                .build());
    }

    @PutMapping("/join")
    public ResponseEntity<Object> join(@RequestBody UserJoinRequest joinRq) throws Exception {
        UserLoginResponse loginRs;

        loginRs  = userService.join(joinRq);

        return success(loginRs, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest loginRq) throws Exception {
        UserLoginResponse loginRs = userService.login(loginRq);

        return success(loginRs);
    }

    @GetMapping("/info")
    public ResponseEntity<Object> info(@RequestBody UserDto requestDto) throws Exception {
        UserDto responseDto = userService.getUserByUsername(requestDto.getUsername());

        return success(responseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody UserDto user) throws Exception {
        userService.refresh(user);
        return success(user);
    }

}
