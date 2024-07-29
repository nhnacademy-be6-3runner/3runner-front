package com.nhnacademy.front.book.book.dto.response;

import java.time.ZonedDateTime;

import lombok.Builder;

/**
 * 책의 상세보기 response.
 *
 * @author 한민기
 *
 * @param id            책의 아이디
 * @param title            책 제목
 * @param description    책 설명
 * @param publishedDate    책 출판일
 * @param price            책 정가
 * @param quantity        책 수량
 * @param sellingPrice    책 판매가
 * @param viewCount        책 조회수
 * @param packing        책 포장여부
 * @param author        책 작가
 * @param isbn            책 isbn
 * @param publisher        책 출판사
 * @param imagePath        책 이미지
 */
@Builder
public record ReadDetailBookResponse(
	long id,
	String title,
	String description,
	ZonedDateTime publishedDate,
	int price,
	int quantity,
	int sellingPrice,
	int viewCount,
	boolean packing,
	String author,
	String isbn,
	String publisher,
	String imagePath
) {
}
