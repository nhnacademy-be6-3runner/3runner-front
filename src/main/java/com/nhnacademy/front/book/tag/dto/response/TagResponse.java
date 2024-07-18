package com.nhnacademy.front.book.tag.dto.response;

import lombok.Builder;

@Builder
public record TagResponse(
	long id,
	String name
) {
}
