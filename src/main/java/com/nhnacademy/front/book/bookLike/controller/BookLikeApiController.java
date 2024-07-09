package com.nhnacademy.front.book.bookLike.controller;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.bookLike.serviee.BookLikeService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookLikeApiController {
    private final BookLikeService bookLikeService;

    @GetMapping("/api/books/{bookId}/likes")
    public ApiResponse<Long> countLikeByBookId(@PathVariable("bookId") Long bookId) {
        Long cnt = bookLikeService.countLikeByBookId(bookId);
        return ApiResponse.success(cnt);
    }

    @PostMapping("/api/books/{bookId}/likes")
    public ApiResponse<Void> createBookLike(@PathVariable("bookId") Long bookId, @RequestHeader(value = "Member-Id", required = false) Long memberId) {
        bookLikeService.createLikeBook(bookId, memberId);
        return new ApiResponse<>(new ApiResponse.Header(true, 200));
    }

    @DeleteMapping("/api/books/{bookId}/likes")
    public ApiResponse<Void> deleteBookLike(@PathVariable("bookId") Long bookId, @RequestHeader(value = "Member-Id", required = false) Long memberId) {
        bookLikeService.deleteLikeBook(bookId, memberId);
        return new ApiResponse<>(new ApiResponse.Header(true, 200));
    }

    @GetMapping("/api/books/{bookId}/likes/status")
    public ApiResponse<Boolean> isBookLikeByMember(@PathVariable("bookId") Long bookId, @RequestHeader(value = "Member-Id", required = false) Long memberId) {
        Boolean isLiked = bookLikeService.isLikedByMember(bookId, memberId);
        return ApiResponse.success(isLiked);
    }
}
