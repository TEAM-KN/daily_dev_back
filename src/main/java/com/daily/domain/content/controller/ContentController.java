package com.daily.domain.content.controller;

import com.daily.adaptor.GmarketAdaptor;
import com.daily.adaptor.LineAdaptor;
import com.daily.domain.content.application.ContentService;
import com.daily.domain.content.domain.Content;
import com.daily.domain.content.dto.ContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;
    private final GmarketAdaptor gmarketAdaptor;
    private final LineAdaptor lineAdaptor;

    @GetMapping("/contents")
    public List<ContentResponse> fetchContents() {
        return contentService.fetchContents();
    }

    @GetMapping("/content/site")
    public List<ContentResponse> fetchContentsBySiteCode(@RequestParam @NotBlank final String siteCode) {
        return contentService.fetchContents(siteCode);
    }

    @GetMapping("/content/{id}")
    public ContentResponse fetchContentId(@PathVariable final Long id) {
        return contentService.fetchContentId(id);
    }

    @GetMapping("/content/test")
    public void test() {
        List<Content> newContentsFromAdaptor = gmarketAdaptor.getNewContentsFromAdaptor("2023-08-17");
        System.out.println(newContentsFromAdaptor.stream().count());
    }

}
