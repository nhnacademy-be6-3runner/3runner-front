package com.nhnacademy.front.purchase.cart.dto.response;


import lombok.Builder;

@Builder
public record ReadAllBookCartMemberResponse(
        Long bookCartId,
        Long bookId,
        int price,
        String url,
        String title,
        int quantity,
        int leftQuantity) {
}
