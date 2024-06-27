package com.nhnacademy.front.purchase.cart.dto.request;


import lombok.Builder;

/**
 * 카트 추가,삭제 폼.
 *
 * @author 김병우
 * @param bookId 도서아이디
 * @param quantity 도서수량
 */
@Builder
public record UpdateBookCartRequest(
        long cartId,
        long bookId,
        int quantity) {
}
