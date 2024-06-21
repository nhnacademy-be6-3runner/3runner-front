package com.nhnacademy.bookstore.book.book.dto.response;

import lombok.Builder;

/**
 * main page, search page 반환활 도서 리스트 dto
 * @author 김은비
 * @param title
 * @param price
 * @param sellingPrice
 * @param author
 */

@Builder
public record BookListResponse(
        String title, int price, int sellingPrice, String author
) {
}
