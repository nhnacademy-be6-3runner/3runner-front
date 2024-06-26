package com.nhnacademy.front.book.categroy.dto.response;

import java.util.List;

import lombok.Builder;

/**
 * 상위 카테고리의 자식 카테고리 조회
 */

@Builder
public record CategoryChildrenResponse(
	long id, String name, List<CategoryChildrenResponse> childrenList
) {

}
