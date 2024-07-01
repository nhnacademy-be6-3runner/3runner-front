package com.nhnacademy.front.book.book.dto.response;

import java.time.ZonedDateTime;

import lombok.Builder;

@Builder
public record ReadDetailBookResponse(
	long id,
	String title,
	String description,
	ZonedDateTime publishedDate,
	int price,
	int quantity,
	int sellingPrice,
	int viewCount,
	boolean packing,
	String author,
	String isbn,
	String publisher,
	String imagePath
) {
}
