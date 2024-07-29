package com.nhnacademy.front.book.categroy.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상위 카테고리의 자식 카테고리 조회.
 *
 * @author 한민기
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CategoryParentWithChildrenResponse {
	private long id;
	private String name;
	@Setter
	private List<CategoryParentWithChildrenResponse> childrenList;
}
