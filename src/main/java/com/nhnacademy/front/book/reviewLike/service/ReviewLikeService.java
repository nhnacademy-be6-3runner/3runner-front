package com.nhnacademy.front.book.reviewLike.service;

public interface ReviewLikeService {
    void createReviewLike(Long reviewId, Long memberId);
    void deleteReviewLike(Long reviewId, Long memberId);
    boolean isReviewLikedByMember(Long reviewId, Long memberId);
    Long countReviewLike(Long reviewId);
}
