package com.nhnacademy.bookstore.purchase.bookCart.dto.request;

import lombok.Builder;
import lombok.Setter;

@Builder
public record CreateBookCartMemberRequest(
	Integer quantity,
	Long bookId,
	Long userId
) {
}
