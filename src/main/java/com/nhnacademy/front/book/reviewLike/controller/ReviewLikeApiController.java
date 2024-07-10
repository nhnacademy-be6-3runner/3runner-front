package com.nhnacademy.front.book.reviewLike.controller;

import com.nhnacademy.front.book.reviewLike.service.ReviewLikeService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/api/review/{reviewId}/like")
    ApiResponse<Void> deleteReviewLike(@PathVariable("reviewId") Long reviewId,
                                       @RequestHeader(value = "Member-Id", required = false) Long memberId) {
        reviewLikeService.deleteReviewLike(reviewId, memberId);
        return new ApiResponse<>(new ApiResponse.Header(true, 200));
    }

    @GetMapping("/api/review/{reviewId}/like/status")
    ApiResponse<Boolean> isReviewLikedByMember(@PathVariable("reviewId") Long reviewId,
                                               @RequestHeader(value = "Member-Id", required = false) Long memberId) {
        boolean isLiked = reviewLikeService.isReviewLikedByMember(reviewId, memberId);
        return ApiResponse.success(isLiked);
    }

    @GetMapping("/api/review/{reviewId}/like/count")
    ApiResponse<Long> countReviewLike(@PathVariable("reviewId") Long reviewId) {
        long cnt = reviewLikeService.countReviewLike(reviewId);
        return ApiResponse.success(cnt);
    }
}
