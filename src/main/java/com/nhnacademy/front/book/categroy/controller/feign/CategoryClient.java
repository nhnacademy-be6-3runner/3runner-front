package com.nhnacademy.front.book.categroy.controller.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhnacademy.front.book.categroy.dto.response.CategoryChildrenResponse;
import com.nhnacademy.front.util.ApiResponse;

@FeignClient(name = "BookCategoryClient", url = "http://${feign.client.url}/bookstore/categories")
public interface CategoryClient {
	@GetMapping
	ApiResponse<List<CategoryChildrenResponse>> readAllTagSet();
}
