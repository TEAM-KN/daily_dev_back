package com.news.dev.api.contents.controller;

import com.news.dev.api.contents.dto.ContentsRequest;
import com.news.dev.api.contents.dto.ContentsResponse;
import com.news.dev.api.contents.service.ContentsService;
import com.news.dev.api.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;
    private final MailService mailService;


    @GetMapping("/cache/test")
    public String test() {

        return "API TEST";
    }

    @GetMapping("/list")
    public ResponseEntity<List<ContentsResponse>> list(@RequestBody ContentsRequest rq) {
        List<ContentsResponse> rs = contentsService.list();

        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @GetMapping("/update")
    public void update() {
        contentsService.update();
    }

    @GetMapping("/mail/test")
    public void sendMail() throws Exception {
        mailService.sendMail();
    }
}
