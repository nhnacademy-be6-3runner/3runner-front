package com.nhnacademy.front.book.review.controller;

import com.nhnacademy.front.book.review.dto.response.ReviewListResponse;
import com.nhnacademy.front.book.review.service.ReviewService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class ReviewApiController {
    private final ReviewService reviewService;

    @GetMapping("/{bookId}/reviews")
    public ApiResponse<Page<ReviewListResponse>> readAllReviewsBuBookId(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "likes,desc") String sort) {
        Page<ReviewListResponse> reviewList = reviewService.readAllReviewsByBookId(bookId, page, size, sort);
        return ApiResponse.success(reviewList);
    }
}


//@GetMapping("/main")
//public ApiResponse<Page<BookListResponse>> readAllBooks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int size, @RequestParam(defaultValue = "publishedDate,desc") String sort) {
//    Page<BookListResponse> bookList = bookService.readAllBooks(page, size, sort);
//    return ApiResponse.success(bookList);
//}