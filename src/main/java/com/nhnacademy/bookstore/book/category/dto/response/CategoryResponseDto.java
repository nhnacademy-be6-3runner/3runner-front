package com.nhnacademy.bookstore.book.category.dto.response;

import com.nhnacademy.bookstore.entity.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 + 부모 카테고리 조회
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    private long id;
    private String name;
    private Category parent;

    public CategoryResponseDto (long id, String name) {
        this.id = id;
        this.name = name;
    }
}