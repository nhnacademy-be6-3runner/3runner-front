package com.nhnacademy.front.book.bookLike.exception;

public class DeleteBookLikeException extends RuntimeException {
    public DeleteBookLikeException() {
        super("좋아요 삭제 중 에러");
    }
}
