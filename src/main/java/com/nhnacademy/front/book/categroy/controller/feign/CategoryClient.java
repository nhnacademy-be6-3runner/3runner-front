package com.nhnacademy.front.book.categroy.controller.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
