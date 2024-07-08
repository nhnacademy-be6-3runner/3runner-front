package com.nhnacademy.bookstore.book.reviewLike.exception;

public class ReviewLikeAlreadyExistsException extends RuntimeException {
    public ReviewLikeAlreadyExistsException() {
        super("이미 생성된 좋아요입니다.");
    }
}
