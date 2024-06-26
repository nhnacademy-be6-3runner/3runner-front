package com.nhnacademy.front.book.categroy.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record BookCategoriesChildrenResponse(long id, String name, List<BookCategoriesChildrenResponse> childrenList) {
}
