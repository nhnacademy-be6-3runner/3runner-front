package com.nhnacademy.front.book.tag.controller;

import com.nhnacademy.front.book.tag.dto.response.TagResponse;
import com.nhnacademy.front.book.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TagController {

    private final TagService tagService;

    @GetMapping("/api/tags")
    public List<TagResponse> readAllBookTags() {
        log.info("book tag Controller 안으로 들어옴");
        return tagService.readAllBookTags();
    }

}
