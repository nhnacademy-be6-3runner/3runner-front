package com.nhnacademy.bookstore.book.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 카테고리 + 자식 카테고리 조회
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
// TODO record 형으로 수정
public class CategoryParentWithChildrenResponse {
    private long id;
    private String name;
    private List<CategoryChildrenResponse> childrenList;
}
