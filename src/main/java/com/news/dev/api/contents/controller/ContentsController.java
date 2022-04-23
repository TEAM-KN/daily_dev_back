package com.news.dev.api.contents.controller;

import com.news.dev.api.contents.dto.ContentsRequest;
import com.news.dev.api.contents.dto.ContentsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contents")
public class ContentsController {

    @GetMapping("/test")
    public String test() {
        return "API TEST";
    }

    @GetMapping("/list")
    public ContentsResponse list(@RequestBody ContentsRequest rq) {
        ContentsResponse rs = null;

        return rs;
    }
}
