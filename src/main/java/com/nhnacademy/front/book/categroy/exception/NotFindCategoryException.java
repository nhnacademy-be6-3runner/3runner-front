package com.nhnacademy.front.book.categroy.exception;

public class NotFindCategoryException extends RuntimeException {
	public NotFindCategoryException() {
		super("카테고리를 발견할 수 없습니다.");
	}
}
