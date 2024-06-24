package com.nhnacademy.bookstore.book.book.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiCreateBookResponse(
	String title,
	String pubDate,
	String imageUrl,
	List<AladinItem> item

) {

}
