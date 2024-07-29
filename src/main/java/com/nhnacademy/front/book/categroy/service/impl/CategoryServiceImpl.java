package com.nhnacademy.front.book.categroy.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.book.book.exception.InvalidApiResponseException;
import com.nhnacademy.front.book.categroy.controller.feign.CategoryClient;
import com.nhnacademy.front.book.categroy.dto.request.CreateCategoryRequest;
import com.nhnacademy.front.book.categroy.dto.response.CategoryChildrenResponse;
import com.nhnacademy.front.book.categroy.service.CategoryService;
import com.nhnacademy.front.util.ApiResponse;

import lombok.RequiredArgsConstructor;

/**
 * 카테고리 관련 서비스.
 *
 * @author 한민기
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryClient categoryClient;

	@Override
	@Cacheable(value = "CategoryList", cacheManager = "cacheManager")
	public List<CategoryChildrenResponse> readAllCategoryList() {

		ApiResponse<List<CategoryChildrenResponse>> response = categoryClient.readAllCategoryList();
		if (response.getHeader().isSuccessful()) {
			return response.getBody().getData();
		} else {
			throw new InvalidApiResponseException("카테고리 불러오기 실패하였습니다.");
		}
	}

	/**
	 * 관리자가 카테고리 삭제.
	 *
	 * @param categoryId 카테고리 아이디
	 */
	@Override
	@CacheEvict(value = "CategoryList", key = "'allCategories'", cacheManager = "cacheManager")
	public void deleteCategory(Long categoryId) {
		ApiResponse<Void> response = categoryClient.deleteCategory(categoryId);
		if (!response.getHeader().isSuccessful()) {
			throw new InvalidApiResponseException("카테고리 삭제에 실패하였습니다.");
		}
	}

	@Override
	@CacheEvict(value = "CategoryList", key = "'allCategories'", cacheManager = "cacheManager")
	public void addCategory(String name, Long parentId) {
		CreateCategoryRequest request = CreateCategoryRequest.builder().name(name).parentId(parentId).build();

		ApiResponse<Void> response = categoryClient.createCategory(request);
		if (!response.getHeader().isSuccessful()) {
			throw new InvalidApiResponseException("카테고리 추가에 실패하였습니다.");
		}
	}
}
