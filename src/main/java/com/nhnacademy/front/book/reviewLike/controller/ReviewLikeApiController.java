package com.nhnacademy.front.book.reviewLike.controller;

import com.nhnacademy.front.book.reviewLike.service.ReviewLikeService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewLikeApiController {
    private final ReviewLikeService reviewLikeService;

    @PostMapping("/api/review/{reviewId}/like")
    ApiResponse<Void> createReviewLike(@PathVariable("reviewId") Long reviewId,
                                       @RequestHeader(value = "Member-Id", required = false) Long memberId) {
        reviewLikeService.createReviewLike(reviewId, memberId);
        return new ApiResponse<>(new ApiResponse.Header(true, 200));
    }
}
