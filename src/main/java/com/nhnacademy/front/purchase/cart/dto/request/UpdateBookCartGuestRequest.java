package com.nhnacademy.front.purchase.cart.dto.request;


/**
 * 카트 추가,삭제 폼.
 *
 * @author 김병우
 * @param bookId 도서아이디
 * @param quantity 도서수량
 */

public record UpdateBookCartGuestRequest(
        long bookId,
        int quantity) {
}
