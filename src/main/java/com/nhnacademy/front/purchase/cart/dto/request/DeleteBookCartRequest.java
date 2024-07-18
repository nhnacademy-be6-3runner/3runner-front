package com.nhnacademy.front.purchase.cart.dto.request;

import lombok.Builder;

@Builder
public record DeleteBookCartRequest(
        long cartId,
        long bookCartId) {
}
