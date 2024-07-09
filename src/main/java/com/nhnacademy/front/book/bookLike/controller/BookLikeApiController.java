package com.nhnacademy.front.book.bookLike.controller;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.bookLike.serviee.BookLikeService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookLikeApiController {
    private final BookLikeService bookLikeService;

    @GetMapping("/api/books/{bookId}/likes")
    public ApiResponse<Long> countLikeByBookId(@PathVariable("bookId") Long bookId) {
        Long cnt = bookLikeService.countLikeByBookId(bookId);
        return ApiResponse.success(cnt);
    }
}
