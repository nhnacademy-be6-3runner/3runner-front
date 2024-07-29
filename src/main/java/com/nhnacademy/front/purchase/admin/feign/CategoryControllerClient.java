package com.nhnacademy.front.purchase.admin.feign;

import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.global.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "CategoryControllerClient", url = "${feign.client.url}", configuration = FeignConfiguration.class)
public interface CategoryControllerClient {
	@GetMapping("/bookstore/categories")
	ApiResponse<List<CategoryParentWithChildrenResponse>> readAllCategories();
}
