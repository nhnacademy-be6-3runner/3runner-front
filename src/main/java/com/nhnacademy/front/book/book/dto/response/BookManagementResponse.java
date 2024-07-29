package com.nhnacademy.front.book.book.dto.response;

/**
 * 관리자 페이지 책 정보 response 입니다.
 *
 * @author 한민기
 *
 * @param id            아이디
 * @param title            제목
 * @param price            가격
 * @param sellingPrice    판매가격
 * @param author        작가
 * @param quantity        수량
 * @param viewCount        조회수
 */
public record BookManagementResponse(
	long id, String title, int price, int sellingPrice, String author, int quantity, int viewCount
) {
}
