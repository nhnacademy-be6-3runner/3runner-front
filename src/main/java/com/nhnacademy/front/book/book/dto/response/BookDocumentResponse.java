package com.nhnacademy.front.book.book.dto.response;

import java.util.List;

import lombok.Builder;

/**
 * 책 검색 결과를 가져올 Response.
 *
 * @author 한민기
 *
 * @param id                책의 아이디
 * @param title                책의 제목
 * @param author            책의 작가
 * @param thumbnail            책의 메인 이미지
 * @param publisher            책의 출판사
 * @param price                책의 정가
 * @param sellingPrice        책의 판매가
 * @param tagList            책의 태그
 * @param categoryList        책의 카테고리
 */

@Builder
public record BookDocumentResponse(
	long id,
	String title,
	String author,
	String thumbnail,
	String publisher,
	int price,
	int sellingPrice,
	List<String> tagList,
	List<String> categoryList) {
}
