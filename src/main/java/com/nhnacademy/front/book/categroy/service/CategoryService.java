package com.nhnacademy.front.book.categroy.service;

import java.util.List;

import com.nhnacademy.front.book.categroy.dto.response.CategoryChildrenResponse;

public interface CategoryService {
	List<CategoryChildrenResponse> readAllCategoryList();

	void deleteCategory(Long categoryId);

	void addCategory(String name, Long parent);
}
