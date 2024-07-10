package com.nhnacademy.front.book.categroy.controller.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.categroy.dto.request.CreateCategoryRequest;
import com.nhnacademy.front.book.categroy.dto.response.CategoryChildrenResponse;
import com.nhnacademy.front.util.ApiResponse;

@FeignClient(name = "BookCategoryClient", url = "http://${feign.client.url}/bookstore/categories")
public interface CategoryClient {
	@GetMapping
	ApiResponse<List<CategoryChildrenResponse>> readAllCategoryList();

	@DeleteMapping("/{categoryId}")
	ApiResponse<Void> deleteCategory(@PathVariable("categoryId") Long categoryId);

	@PostMapping
	ApiResponse<Void> createCategory(@RequestBody CreateCategoryRequest dto);

	/**
	 * 카테고리에 관련된 도서페이지 조회 메서드
	 * @param page 페이지
	 * @param size 사이즈
	 * @param sort 정렬
	 * @param categoryId 카테고리 넘버
	 * @return 한민기
	 */
	@GetMapping("/books")
	ApiResponse<Page<BookListResponse>> readCategoryAllBooks(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "12") int size,
		@RequestParam(defaultValue = "publishedDate,desc") String sort,
		@RequestParam Long categoryId);
}
