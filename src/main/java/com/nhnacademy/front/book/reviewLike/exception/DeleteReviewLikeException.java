package com.nhnacademy.front.book.reviewLike.exception;

public class DeleteReviewLikeException extends RuntimeException {
    public DeleteReviewLikeException() {
        super("리뷰 좋아요 삭제 중 에러 발생");
    }
}
