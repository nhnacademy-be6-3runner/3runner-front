package com.nhnacademy.bookstore.book.reviewLike.exception;

public class ReviewLikeNotExistsException extends RuntimeException {
    public ReviewLikeNotExistsException() {
        super("존제하지 않는 리뷰 좋아요입니다.");
    }
}
