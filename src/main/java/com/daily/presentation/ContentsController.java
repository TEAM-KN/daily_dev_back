package com.daily.presentation;

import com.daily.adaptor.NaverNewsAdaptor;
import com.daily.domain.contents.application.ContentsService;
import com.daily.domain.contents.domain.Contents;
import com.daily.global.exception.ResponseEntityHandler;
import com.daily.domain.contents.dto.ContentsRequest;
import com.daily.domain.contents.dto.ContentsResponse;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContentsController extends ResponseEntityHandler {

    private final ContentsService contentsService;
    private final NaverNewsAdaptor naverNewsAdaptor;

    @GetMapping("/contents")
    public ResponseEntity<Object> fetchContents(@RequestBody ContentsRequest rq) {
        List<ContentsResponse> rs = contentsService.fetchContents();
        return success(rs);
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<Object> fetchContentId(@PathVariable final Long id) {
        ContentsResponse contentsResponse = contentsService.fetchContentsId(id);
        return success(contentsResponse);
    }

    @GetMapping("/content/update")
    public void update() {
        contentsService.update();
    }

}
