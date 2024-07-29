package com.nhnacademy.front.book.book.dto.response;

import lombok.Builder;

/**
 * 도서 리스트 조회 dto 입니다.
 *
 * @param id 도서 아이디
 * @param title 도서 제목
 * @param price 도서 가격
 * @param sellingPrice 도서 할인가
 * @param author 도서 작가
 * @param thumbnail 메인 사진
 *
 * @author 김은비
 */
@Builder
public record BookListResponse(
	long id, String title, int price, int sellingPrice, String author, String thumbnail
) {
}