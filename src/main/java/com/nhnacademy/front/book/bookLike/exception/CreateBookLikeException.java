package com.nhnacademy.front.book.bookLike.exception;

public class CreateBookLikeException extends RuntimeException {
    public CreateBookLikeException() {
        super("좋아요 생성 중 에러");
    }
}
