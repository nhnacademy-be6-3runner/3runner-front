package com.nhnacademy.front.book.review.controller;

import com.nhnacademy.front.book.review.dto.request.DeleteReviewRequest;
import com.nhnacademy.front.book.review.dto.request.UserCreateReviewRequest;
import com.nhnacademy.front.book.review.dto.response.ReviewAdminListResponse;
import com.nhnacademy.front.book.review.dto.response.ReviewDetailResponse;
import com.nhnacademy.front.book.review.dto.response.ReviewListResponse;
import com.nhnacademy.front.book.review.service.ReviewService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewApiController {
    private final ReviewService reviewService;

    @GetMapping("/books/{bookId}/reviews")
    public ApiResponse<Page<ReviewListResponse>> readAllReviewsByBookId(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "likes,desc") String sort) {
        Page<ReviewListResponse> reviewList = reviewService.readAllReviewsByBookId(bookId, page, size, sort);
        return ApiResponse.success(reviewList);
    }

    @GetMapping("/books/{bookId}/reviews/count")
    public ApiResponse<Long> countReviewsByBookId(@PathVariable Long bookId) {
        return ApiResponse.success(reviewService.countReviewsByBookId(bookId));
    }

    @GetMapping("/books/{bookId}/reviews/avg")
    public ApiResponse<Double> avgReviewsByBookId(@PathVariable Long bookId) {
        return ApiResponse.success(reviewService.getAverageRatingByBookId(bookId));
    }

    @GetMapping("/books/mypage/reviews")
    public ApiResponse<Page<ReviewListResponse>> readAllReviewsByMemberId(@RequestHeader(value = "Member-Id", required = false) Long memberId,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "5") int size) {
        Page<ReviewListResponse> reviewList = reviewService.readAllReviewsByMemberId(memberId, page, size);
        return ApiResponse.success(reviewList);
    }

    @GetMapping("/reviews/{reviewId}")
    public ApiResponse<ReviewDetailResponse> getReviewDetail(@PathVariable long reviewId) {
        ReviewDetailResponse response = reviewService.readReviewDetail(reviewId);
        return ApiResponse.success(response);
    }

    @GetMapping("/reviews")
    public ApiResponse<Page<ReviewAdminListResponse>> readAllReview(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        Page<ReviewAdminListResponse> responses = reviewService.readAllReviews(page, size);
        return ApiResponse.success(responses);
    }

    @PostMapping(value = "/{purchaseBookId}/review", consumes = "multipart/form-data")
    public ApiResponse<Long> createReview(@PathVariable long purchaseBookId, @RequestHeader(value = "Member-id", required = false) Long memberId,
                                          UserCreateReviewRequest reviewRequest) {
        Long reviewId = reviewService.createReview(purchaseBookId, memberId, reviewRequest);
        return ApiResponse.success(reviewId);
    }
}