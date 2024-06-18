package com.nhnacademy.bookstore.book.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 자식 카테고리 조회
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
// TODO record 형으로 수정
public class CategoryChildrenResponseDto {
    private long id;
    private String name;
}
