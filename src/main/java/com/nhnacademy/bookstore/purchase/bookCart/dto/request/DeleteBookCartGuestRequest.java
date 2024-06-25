package com.nhnacademy.bookstore.purchase.bookCart.dto.request;

import lombok.Builder;

@Builder
public record DeleteBookCartGuestRequest(
        long cartId,
        long bookCartId) {
}
