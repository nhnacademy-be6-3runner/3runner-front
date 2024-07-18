package com.nhnacademy.front.purchase.cart.dto.response;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ReadBookCartBook(
	String title,
	String description,
	ZonedDateTime publishedDate,
	int price,
	int quantity,
	int sellingPrice,
	boolean packing,
	String author,
	String publisher
) {
}
