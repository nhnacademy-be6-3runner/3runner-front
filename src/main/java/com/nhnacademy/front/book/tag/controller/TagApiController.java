package com.nhnacademy.front.book.tag.controller;

import com.nhnacademy.front.book.tag.dto.response.TagResponse;
import com.nhnacademy.front.book.tag.service.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 도서 태그 api 컨트롤러.
 *
 * @author 한민기
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class TagApiController {

	private final TagService tagService;

	/**
	 * 책 등록시 태그 가져오기.
	 *
	 * @return 책등록시 가져오기
	 */
	@GetMapping("/api/tags")
	public List<TagResponse> readAllBookTags() {
		return tagService.readAllBookTags();
	}

	/**
	 * 관리자 페이지에서 태그 page 가져오기.
	 *
	 * @return 태그 페이지로 가져오기
	 */
	@GetMapping("/api/tagPages")
	public ResponseEntity<Page<TagResponse>> readAllBookTagPages(@RequestParam int page) {
		Page<TagResponse> bookPage = tagService.readAllAdminBookTags(page, 20);
		return ResponseEntity.ok(bookPage);
	}

}
