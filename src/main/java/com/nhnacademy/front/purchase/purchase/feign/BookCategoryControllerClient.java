package com.nhnacademy.front.purchase.purchase.feign;

import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "BookCategoryControllerClient", url = "${feign.client.url}")
public interface BookCategoryControllerClient {
	@GetMapping("/bookstore/books/{bookId}/categories")
	ApiResponse<List<CategoryParentWithChildrenResponse>> readCategories(@PathVariable Long bookId);
}
