package com.nhnacademy.front.book.bookTag.controller;

import com.nhnacademy.front.book.bookTag.dto.response.BookTagResponse;
import com.nhnacademy.front.book.bookTag.service.BookTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController("/api/tags")
@RequiredArgsConstructor
public class BookTagController {

    private final BookTagService bookTagService;

    public Set<BookTagResponse> readAllBookTags() {
        return bookTagService.readAllBookTags();
    }

}
