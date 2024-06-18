package com.nhnacademy.bookstore.book.category.service;

import com.nhnacademy.bookstore.book.category.dto.request.CreateCategoryRequestDto;
import com.nhnacademy.bookstore.book.category.dto.request.UpdateCategoryRequestDto;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryChildrenResponseDto;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryParentWithChildrenResponseDto;
import com.nhnacademy.bookstore.book.category.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    // 카테고리 등록
    void createCategory(CreateCategoryRequestDto dto);
    // 카테고리 업데이트
    void updateCategory(long id, UpdateCategoryRequestDto dto);
    // 카테고리 삭제
    void deleteCategory(long id);
    // 카테고리 전체 조회
    List<CategoryResponseDto> getCategories();
    // 상위 카테고리 목록 조회
    List<CategoryResponseDto> getParentCategories();
    // 특정 상위 카테고리의 하위 카테고리 목록 조회
    List<CategoryChildrenResponseDto> getChildrenCategoriesByParentId(long id);
}
