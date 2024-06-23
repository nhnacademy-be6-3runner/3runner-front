package com.nhnacademy.bookstore.purchase.bookCart.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 카트 추가,삭제 폼.
 *
 * @author 김병우
 * @param bookId 도서아이디
 * @param quantity 도서수량
 */

public record UpdateBookCartGuestRequest(
        @NotNull(message = "bookCartId is mandatory") Long bookId,
        @Min(value = 1, message = "min value is 0") int quantity) {
}
