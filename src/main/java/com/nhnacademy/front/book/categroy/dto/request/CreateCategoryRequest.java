package com.nhnacademy.front.book.categroy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * 카테고리 만들때 사용하는 레코드.
 *
 * @author 한민기
 *
 * @param name            카테고리 이름
 * @param parentId        부모 카테고리 id
 */
@Builder
public record CreateCategoryRequest(
	@NotBlank String name, Long parentId
) {
}
