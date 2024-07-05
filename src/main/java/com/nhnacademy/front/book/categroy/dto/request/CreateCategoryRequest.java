package com.nhnacademy.front.book.categroy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateCategoryRequest(
	@NotBlank String name, Long parentId
) {
}
