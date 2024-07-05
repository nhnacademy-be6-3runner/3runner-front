package com.nhnacademy.front.book.tag.controller.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.book.tag.dto.response.TagResponse;
import com.nhnacademy.front.util.ApiResponse;

@FeignClient(name = "BookTagClient", url = "http://${feign.client.url}/bookstore/tags")
public interface TagClient {
	@GetMapping
	ApiResponse<List<TagResponse>> readAllTagSet();

	@GetMapping("/admin")
	ApiResponse<Page<TagResponse>> readAllAdminTags(@RequestParam("page") int page,
		@RequestParam("size") int size);

	@PostMapping
	ApiResponse<Void> createTage(@RequestParam String name);

	@DeleteMapping
	ApiResponse<Void> deleteTage(@RequestParam Long tagId);
}
