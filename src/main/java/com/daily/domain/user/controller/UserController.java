package com.daily.domain.user.controller;

import com.daily.global.common.response.CommonResponse;
import com.daily.domain.user.application.UserService;
import com.daily.domain.user.dto.UserDto;
import com.daily.domain.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDto.UserWithSite fetchUser(@RequestParam @NotBlank String email) {
        return userService.fetchUser(email);
    }

    @PostMapping("/sites")
    public CommonResponse saveSites(@RequestBody @Valid UserRequest.UserFromSiteRequest request) {
        return userService.saveUserSites(request);
    }

    @DeleteMapping("/leave")
    public CommonResponse leaveUser(@RequestParam @NotBlank String email) {
        return userService.leaveUser(email);
    }

    @DeleteMapping("/sites")
    public CommonResponse deleteSites(@RequestBody @Valid UserRequest.UserFromSiteRequest request) {
        return userService.deleteUserSites(request);
    }


}
