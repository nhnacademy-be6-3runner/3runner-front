package com.nhnacademy.bookstore.book.book.dto.response;

import lombok.Builder;

/**
 * main page, search page 반환활 도서 리스트 dto
 * @author 김은비
 * @param title 책 제목
 * @param price 책 가격
 * @param sellingPrice 할인 가격
 * @param author 작가
 */

@Builder
public record BookListResponse(
        String title, int price, int sellingPrice, String author, String thumbnail
) {
}
