package com.nhnacademy.bookstore.book.category.repository;

import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;

import java.util.List;
import java.util.Set;

/**
 * query dsl custom repository
 * @author 김은비
 */
public interface CategoryCustomRepository {
    // 모든 카테고리 조회
    List<CategoryResponse> findCategories();

    // 상위 카테고리 조회
    List<CategoryResponse> findTopCategories();

    // 상위 + 하위 카테고리 조회
    List<CategoryParentWithChildrenResponse> findParentWithChildrenCategories();

    // 하위 카테고리 조회
    List<CategoryChildrenResponse> findChildrenCategoriesByParentId(Long id);
}
