package com.nhnacademy.front.book.book.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record BookDocumentResponse(
	long id,
	String title,
	String author,
	String thumbnail,
	String publisher,
	String publishedDate,
	int price,
	int sellingPrice,
	List<String> tagList,
	List<String> categoryList) {
}
