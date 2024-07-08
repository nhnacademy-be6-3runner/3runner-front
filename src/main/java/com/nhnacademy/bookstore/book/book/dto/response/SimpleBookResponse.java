package com.nhnacademy.bookstore.book.book.dto.response;

/**
 * 리뷰, 주문 내역 조회 등에 쓰일 도서 response 입니다.
 *
 * @param title     책 제목
 * @param author    작가
 * @param thumbnail 이미지
 * @author 김은비
 */
public record SimpleBookResponse(
        String title, String author, String thumbnail
) {
}
