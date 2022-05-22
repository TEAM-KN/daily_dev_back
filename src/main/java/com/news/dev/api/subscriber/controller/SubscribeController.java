package com.news.dev.api.subscriber.controller;

import com.news.dev.api.subscriber.service.SubscribeService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribe")
@RequiredArgsConstructor
public class SubscribeController {

    private final SubscribeService subscribeService;

    @PostMapping("/add")
    public void add(Authentication authentication) throws Exception {

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String username = user.getUsername();
        subscribeService.add(username);
    }

    @DeleteMapping("/cancle")
    public void cancle(Authentication authentication) throws Exception {

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String username = user.getUsername();
        subscribeService.add(username);
    }
}
