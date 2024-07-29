package com.nhnacademy.front.book.categroy.dto.response;

import java.util.List;
import lombok.Builder;

/**
 * 상위 카테고리의 자식 카테고리 조회 레코드.
 *
 * @author 한민기
 *
 * @param id            아이디
 * @param name            이름
 * @param childrenList    자식 카테고리
 */
@Builder
public record CategoryChildrenResponse(
	long id, String name, List<CategoryChildrenResponse> childrenList
) {

}
