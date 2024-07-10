package com.nhnacademy.front.book.reviewLike.service.impl;

import com.nhnacademy.front.book.book.exception.InvalidApiResponseException;
import com.nhnacademy.front.book.reviewLike.controller.feign.ReviewLikeClient;
import com.nhnacademy.front.book.reviewLike.exception.CreateReviewLikeException;
import com.nhnacademy.front.book.reviewLike.exception.DeleteReviewLikeException;
import com.nhnacademy.front.book.reviewLike.service.ReviewLikeService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewLikeServiceImpl implements ReviewLikeService {
    private final ReviewLikeClient reviewLikeClient;

    @Override
    public void createReviewLike(Long reviewId, Long memberId) {
        ApiResponse<Void> response =  reviewLikeClient.createReviewLike(reviewId, memberId);
        if (!response.getHeader().isSuccessful()) {
            throw new CreateReviewLikeException();
        }
    }

    @Override
    public void deleteReviewLike(Long reviewId, Long memberId) {
        ApiResponse<Void> response = reviewLikeClient.deleteReviewLike(reviewId, memberId);
        if (!response.getHeader().isSuccessful()) {
            throw new DeleteReviewLikeException();
        }
    }

    @Override
    public boolean isReviewLikedByMember(Long reviewId, Long memberId) {
        ApiResponse<Boolean> response = reviewLikeClient.isReviewLikedByMember(reviewId, memberId);
        if (!response.getHeader().isSuccessful()) {
            throw new InvalidApiResponseException("리뷰 좋아요 여부 조회 중 에러");
        }
        return response.getBody().getData();
    }

    @Override
    public Long countReviewLike(Long reviewId) {
        ApiResponse<Long> response = reviewLikeClient.countReviewLike(reviewId);
        if (!response.getHeader().isSuccessful()) {
            throw new InvalidApiResponseException("리뷰 좋아요 카운트 중 에러");
        }
        return response.getBody().getData();
    }
}
