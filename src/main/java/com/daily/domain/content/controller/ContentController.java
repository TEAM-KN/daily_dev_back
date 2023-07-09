package com.daily.domain.content.controller;

import com.daily.domain.content.application.ContentService;
import com.daily.domain.content.dto.ContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/contents")
    public List<ContentResponse> fetchContents() {
        return contentService.fetchContents();
    }

    @GetMapping("/content/site")
    public List<ContentResponse> fetchContentsBySiteCode(@RequestParam @NotBlank String siteCode) {
        return contentService.fetchContents(siteCode);
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
