package com.nhnacademy.bookstore.book.category.service;

import com.nhnacademy.bookstore.book.category.dto.request.CreateCategoryRequest;
import com.nhnacademy.bookstore.book.category.dto.request.UpdateCategoryRequest;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponse;

import java.util.List;

/**
 * 키테고리 서비스 인터페이스
 *
 * @author 김은비
 */
public interface CategoryService {
    // 카테고리 등록
    void createCategory(CreateCategoryRequest dto);

    // 카테고리 업데이트
    void updateCategory(long id, UpdateCategoryRequest dto);

    // 카테고리 삭제
    void deleteCategory(long id);

    // 카테고리 단건 조회
    CategoryResponse getCategory(long id);

    // 카테고리 전체 조회
    List<CategoryResponse> getCategories();

    List<CategoryResponse> getParentCategories();

    // 특정 상위 카테고리의 하위 카테고리 목록 조회
    List<CategoryChildrenResponse> getChildrenCategoriesByParentId(long id);

    List<CategoryParentWithChildrenResponse> getCategoriesWithChildren();
}
