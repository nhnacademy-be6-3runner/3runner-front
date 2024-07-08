package com.nhnacademy.bookstore.book.reviewLike.exception;

public class DeleteReviewLikeException extends RuntimeException {
    public DeleteReviewLikeException() {
        super("좋아요를 삭제할 수 없습니다.");
    }
}
