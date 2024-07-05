package com.nhnacademy.bookstore.book.review.dto.response;

/**
 * 주문한 책에 대한 검색 response 입니다.
 *
 * @param thumbnail
 * @param bookTitle
 * @param author
 * @author 김은비
 */
public record SearchPurchaseBookResponse(
        String thumbnail,
        String bookTitle,
        String author
) {
}
