package com.nhnacademy.front.book.categroy.exception;

/**
 * 없는 카테고리를 조회 했을 때 사용하는 Exception.
 *
 * @author 한민기
 */
public class NotFindCategoryException extends RuntimeException {
	public NotFindCategoryException() {
		super("카테고리를 발견할 수 없습니다.");
	}
}
