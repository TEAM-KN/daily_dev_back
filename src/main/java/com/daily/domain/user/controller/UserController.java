package com.daily.domain.user.controller;

import com.daily.domain.user.application.UserService;
import com.daily.domain.user.dto.UserDto;
import com.daily.domain.user.dto.UserRequest;
import com.daily.global.common.response.CommonResponse;
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

    @PostMapping
    public CommonResponse saveUser(@RequestBody @Valid UserRequest.Update request) {
        return userService.saveUser(request);
    }

    @PostMapping("/check")
    public CommonResponse checkUser(@RequestBody @Valid UserRequest.Password request) {
        return userService.checkUser(request);
    }

    @PostMapping("/password")
    public CommonResponse savePassword(@RequestBody @Valid UserRequest.Password request) {
        return userService.savePassword(request);
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
    public CommonResponse deleteSites(@RequestParam @NotBlank String email) {
        return userService.deleteUserSites(email);
    }


}
