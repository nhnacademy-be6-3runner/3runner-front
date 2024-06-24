package com.nhnacademy.bookstore.book.book.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AladinItem(
	String title,
	String author,
	String pubDate,
	String description,
	String isbn13,
	int priceSales,
	int priceStandard,
	String cover,
	String categoryName,
	String publisher
) {
}
