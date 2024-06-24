package com.nhnacademy.bookstore.book.book.dto.request;

import lombok.Builder;

@Builder
public record AladinRequest(
	String ttbkey,
	String ItemIdType,
	String ItemId,
	String QueryType,
	String output
) {
}
