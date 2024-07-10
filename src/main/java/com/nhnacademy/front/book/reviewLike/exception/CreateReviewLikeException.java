package com.nhnacademy.front.book.reviewLike.exception;

public class CreateReviewLikeException extends RuntimeException {
    public CreateReviewLikeException() {
        super("리뷰 좋아요 생성 중 에러");
    }
}
