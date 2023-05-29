package com.daily.domain.user.presentation;

import com.daily.domain.user.application.UserService;
import com.daily.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto fetchUser(@PathVariable @NotBlank(message = "이메일은 필수 값 입니다.") String id) {
        return userService.fetchUser(id);
    }

}
