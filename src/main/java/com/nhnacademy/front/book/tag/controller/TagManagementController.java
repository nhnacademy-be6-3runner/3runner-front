package com.nhnacademy.front.book.tag.controller;

import com.nhnacademy.front.book.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 태그 관리 컨트롤러.
 *
 * @author 한민기
 */
@Controller
@RequestMapping
@RequiredArgsConstructor
public class TagManagementController {
	private final TagService tagService;

	/**
	 * 관리자 페이지 태그 보기.
	 *
	 * @return 관리자 페이지 태그 보기
	 */
	@GetMapping("/admin/tag/management")
	public String adminTagManagement() {
		return "admin/admin_tag";
	}

	/**
	 * 관리자 페이지 태그 만들기.
	 *
	 * @param name 만들 태그 이름
	 * @return 관리자 페이지 태그로 돌아가기
	 */
	@PostMapping("/admin/tag/management")
	public String adminTagAdd(@RequestParam(name = "tag-name") String name) {
		tagService.createTag(name);
		return "admin/admin_tag";
	}

	/**
	 * 관리자 테그 삭제.
	 *
	 * @param tagId 삭제할 테그 아이디
	 * @return 삭제후 관리로 이동
	 */
	@GetMapping("/admin/tag/delete/{tagId}")
	public String adminTagDelete(@PathVariable Long tagId) {
		tagService.deleteTag(tagId);
		return "admin/admin_tag";
	}
}
