package com.nhnacademy.front.book.reviewLike.service.impl;

import com.nhnacademy.front.book.reviewLike.controller.feign.ReviewLikeClient;
import com.nhnacademy.front.book.reviewLike.exception.CreateReviewLikeException;
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
}
