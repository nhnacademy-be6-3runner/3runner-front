package com.nhnacademy.bookstore.book.category.dto.response;

import lombok.*;

import java.util.List;

/**
 * 상위 카테고리의 자식 카테고리 조회
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CategoryParentWithChildrenResponse {
    private long id;
    private String name;
    @Setter
    private List<CategoryChildrenResponse> childrenList;

    public CategoryParentWithChildrenResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
