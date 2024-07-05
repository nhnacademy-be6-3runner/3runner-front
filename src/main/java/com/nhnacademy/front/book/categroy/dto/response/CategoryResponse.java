package com.nhnacademy.front.book.categroy.dto.response;

import lombok.Builder;

@Builder
public record CategoryResponse(long id, String name) {
}
