package com.daily.contents.presentation;

import com.daily.adaptor.NaverNewsAdaptor;
import com.daily.contents.application.ContentsService;
import com.daily.comn.exception.ResponseEntityHandler;
import com.daily.contents.dto.ContentsRequest;
import com.daily.contents.dto.ContentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
