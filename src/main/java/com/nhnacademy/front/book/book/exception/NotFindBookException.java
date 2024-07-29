package com.nhnacademy.front.book.book.exception;

/**
 * 책을 찾지 못하였을떄 사용하는 Exception.
 *
 * @author 한민기
 *
 */
public class NotFindBookException extends RuntimeException {
	public NotFindBookException() {
		super("책을 찾지 못하였습니다.");
	}
}
