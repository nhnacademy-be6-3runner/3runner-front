package com.nhnacademy.bookstore.purchase.bookCart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * 카트 생성 폼.
 *
 * @author 김병우
 * @param bookId 도서아이디
 * @param quantity 도서수량
 */
public record CreateBookCartGuestRequest(
        long bookId,
        int quantity) {
    }
