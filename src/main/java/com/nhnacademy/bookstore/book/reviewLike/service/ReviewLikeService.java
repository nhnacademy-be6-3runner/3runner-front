package com.nhnacademy.bookstore.book.reviewLike.service;

/**
 * 리뷰 좋아요 기능을 위한 서비스 인터페이스입니다.
 *
 * @author 김은비
 */
public interface ReviewLikeService {
    void createReviewLike(long reviewId, long memberId);

    void deleteReviewLike(long reviewId, long memberId);

    Long countReviewLike(long reviewId);
}
