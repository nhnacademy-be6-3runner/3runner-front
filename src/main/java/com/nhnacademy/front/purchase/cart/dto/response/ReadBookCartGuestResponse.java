package com.nhnacademy.front.purchase.cart.dto.response;

import lombok.Builder;

/**
 * 카트 응답 폼.
 *
 * @author 김병우
 * @param bookCartId
 * @param title
 * @param quantity
 * @param price
 */
@Builder
public record ReadBookCartGuestResponse(
        Long bookCartId,
        Long bookId,
        int price,
        String url,
        String title,
        int quantity){
}
