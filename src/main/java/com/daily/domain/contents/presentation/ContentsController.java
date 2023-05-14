package com.daily.domain.contents.presentation;

import com.daily.domain.contents.application.ContentsService;
import com.daily.comn.exception.ResponseEntityHandler;
import com.daily.domain.contents.dto.ContentsRequest;
import com.daily.domain.contents.dto.ContentsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContentsController extends ResponseEntityHandler {

    private final ContentsService contentsService;

    @GetMapping("/contents")
    public List<ContentsResponse> fetchContents(@RequestBody ContentsRequest rq) {
        return contentsService.fetchContents();
    }

    @GetMapping("/content/{id}")
    public ContentsResponse fetchContentId(@PathVariable Long id) {
        return contentsService.fetchContentsId(id);
    }

    @GetMapping("/content/update")
    public void update() {
        contentsService.update();
    }

}
