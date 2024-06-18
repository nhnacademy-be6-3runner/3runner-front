package com.nhnacademy.bookstore.book.category.repository;

import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponseDto;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponseDto;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponseDto;
import com.nhnacademy.bookstore.entity.category.Category;

import java.util.List;

public interface CategoryCustomRepository {
    // 모든 카테고리 조회
    List<CategoryResponseDto> findCategories();

    // 단일 카테고리 조회

    // 상위 카테고리 조회
    List<CategoryResponseDto> findParentCategories();

    // 상위 + 하위 카테고리 조회
    List<CategoryParentWithChildrenResponseDto> findParentWithChildrenCategories();

    // 하위 카테고리 조회
    List<CategoryChildrenResponseDto> findChildrenCategoriesByParentId(Long id);
}
