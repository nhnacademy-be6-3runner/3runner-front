package com.nhnacademy.bookstore.purchase.bookCart.dto.response;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Value;

/**
 * 카트 응답 폼
 *
 * @author 김병우
 * @param bookId 도서 아이디
 * @param cartId 장바구니 아이디
 * @param bookCartId 도서장바구니 아이디
 * @param quantity 수량
 * @param createdAt 생성일자
 */
@Builder
public record ReadBookCartGuestResponse(
        Long bookId,
        Long cartId,
        Long bookCartId,
        int quantity,
        ZonedDateTime createdAt
) { }
