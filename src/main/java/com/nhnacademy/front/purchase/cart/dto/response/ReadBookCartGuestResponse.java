package com.nhnacademy.front.purchase.cart.dto.response;

import lombok.Builder;

/**
 * 카트 응답 폼.
 *
 * @param bookCartId
 * @param bookId
 * @param price
 * @param url
 * @param title
 * @param quantity
 */
@Builder
public record ReadBookCartGuestResponse(
        Long bookCartId,
        Long bookId,
        int price,
        String url,
        String title,
        int quantity,
        int leftQuantity){
}
