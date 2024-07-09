package com.nhnacademy.front.book.bookLike.exception;

public class CountBookLikeException extends RuntimeException {
    public CountBookLikeException() {
        super("좋아요 조회 중 에러");
    }
}
