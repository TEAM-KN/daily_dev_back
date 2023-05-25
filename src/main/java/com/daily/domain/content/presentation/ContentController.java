package com.daily.domain.content.presentation;

import com.daily.domain.content.application.ContentService;
import com.daily.comn.exception.ResponseEntityHandler;
import com.daily.domain.content.dto.ContentRequest;
import com.daily.domain.content.dto.ContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContentController extends ResponseEntityHandler {

    private final ContentService contentService;

    @GetMapping("/contents")
    public List<ContentResponse> fetchContents(@RequestBody ContentRequest rq) {
        return contentService.fetchContents();
    }

    @GetMapping("/content/{id}")
    public ContentResponse fetchContentId(@PathVariable Long id) {
        return contentService.fetchContentsId(id);
    }

    @GetMapping("/content/update")
    public void update() {
        contentService.update();
    }

}
