package com.nhnacademy.bookstore.book.category.repository;

import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;

import java.util.List;
import java.util.Set;

public interface CategoryCustomRepository {
    // 모든 카테고리 조회
    Set<CategoryResponse> findCategories();

    // 단일 카테고리 조회

    // 상위 카테고리 조회
    Set<CategoryResponse> findParentCategories();

    // 상위 + 하위 카테고리 조회
    Set<CategoryParentWithChildrenResponse> findParentWithChildrenCategories();

    // 하위 카테고리 조회
    Set<CategoryChildrenResponse> findChildrenCategoriesByParentId(Long id);
}
