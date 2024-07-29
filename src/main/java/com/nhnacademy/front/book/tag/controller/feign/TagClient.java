package com.nhnacademy.front.book.tag.controller.feign;

import com.nhnacademy.front.book.tag.dto.response.TagResponse;
import com.nhnacademy.front.util.ApiResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 도서 태그 관련 client.
 *
 * @author 한민기
 */
@FeignClient(name = "BookTagClient", url = "${feign.client.url}/bookstore/tags")
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
