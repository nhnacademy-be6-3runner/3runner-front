package com.nhnacademy.bookstore.book.bookTag.exception;

public class AlreadyExistsBookTagException extends RuntimeException {
    public AlreadyExistsBookTagException(String message) {
        super(message);
    }
}
