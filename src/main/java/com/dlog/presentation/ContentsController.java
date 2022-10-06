package com.dlog.presentation;

import com.dlog.domain.contents.dto.ContentsRequest;
import com.dlog.domain.contents.dto.ContentsResponse;
import com.dlog.domain.contents.application.ContentsService;
import com.dlog.global.exception.ResponseEntityHandler;
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

    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestBody ContentsRequest rq) {
        List<ContentsResponse> rs = contentsService.list();

        return success(rs);
    }

    @GetMapping("/update")
    public void update() {
        contentsService.update();
    }
}
