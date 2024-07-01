package com.nhnacademy.front.book.book.exception;

public class NotFindBookException extends RuntimeException {
	public NotFindBookException() {
		super("책을 찾지 못하였습니다.");
	}
}
