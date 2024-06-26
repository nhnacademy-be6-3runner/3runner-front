package com.nhnacademy.bookstore.purchase.bookCart.dto.request;

import lombok.Builder;

@Builder
public record UpdateBookCartMemberRequest( int quantity, long bookId, long cartId) {
}