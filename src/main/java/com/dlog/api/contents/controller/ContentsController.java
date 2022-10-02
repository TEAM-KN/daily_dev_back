package com.dlog.api.contents.controller;

import com.dlog.api.contents.dto.ContentsRequest;
import com.dlog.api.contents.dto.ContentsResponse;
import com.dlog.api.contents.service.ContentsService;
import com.dlog.api.mail.service.MailService;
import com.dlog.response.ResponseEntityHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentsController extends ResponseEntityHandler {

    private final ContentsService contentsService;
    private final MailService mailService;


    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestBody ContentsRequest rq) {
        List<ContentsResponse> rs = contentsService.list();

        return success(rs);
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
