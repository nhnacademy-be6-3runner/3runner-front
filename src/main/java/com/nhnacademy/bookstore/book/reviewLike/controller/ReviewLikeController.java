package com.nhnacademy.bookstore.book.reviewLike.controller;

import com.nhnacademy.bookstore.book.review.service.ReviewService;
import com.nhnacademy.bookstore.book.reviewLike.service.ReviewLikeService;
import com.nhnacademy.bookstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 리뷰 좋아요 컨트롤러입니다.
 *
 * @author 김은비
 */
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class ReviewLikeController {
    private final ReviewLikeService reviewLikeService;
    private final ReviewService reviewService;

    /**
     * 리뷰 좋아요 생성 메서드입니다.
     *
     * @param reviewId 리뷰 아이디
     * @param memberId 사용자 아이디
     * @return ApiResponse<>
     */
    @PostMapping
    public ApiResponse<Void> createReviewLike(@RequestParam long reviewId, @RequestHeader("Member-Id") Long memberId) {
        reviewLikeService.createReviewLike(reviewId, memberId);
        return new ApiResponse<>(new ApiResponse.Header(true, 201));
    }

    /**
     * 리뷰 좋아요 삭제 메서드입니다.
     *
     * @param reviewLikeId 리뷰 좋아요 아이디
     * @param memberId     사용자 아이디
     * @return ApiResponse<>
     */
    @DeleteMapping
    public ApiResponse<Void> deleteReviewLike(@RequestParam long reviewLikeId, @RequestHeader("Member-Id") Long memberId) {
        reviewLikeService.deleteReviewLike(reviewLikeId, memberId);
        return new ApiResponse<>(new ApiResponse.Header(true, 200));
    }
}
