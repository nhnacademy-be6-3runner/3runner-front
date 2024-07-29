package com.nhnacademy.front.book.categroy.controller;

import com.nhnacademy.front.book.categroy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 카테고리 관리를 담당하는 컨트롤러.
 *
 * @author 한민기
 */
@RequestMapping
@Controller
@RequiredArgsConstructor
public class CategoryManagementController {
	private final CategoryService categoryService;

	/**
	 * 카테고리 관리자 페이지로 넘어가는 메소드.
	 *
	 * @return 관리자 페이지 카테고리 관리
	 */
	@GetMapping("/admin/category/management")
	public String categoryManagement() {
		return "admin/admin_category";
	}

	/**
	 * 카테고리 삭제.
	 *
	 * @param categoryId 삭제할 카테고리 아이디
	 * @return 삭제후 관리로 다시 이동
	 */
	@GetMapping("/admin/category/delete/{categoryId}")
	public String deleteCategory(@PathVariable long categoryId) {
		categoryService.deleteCategory(categoryId);
		return "redirect:/admin/category/management";
	}

	/**
	 * 카테고리 추가 페이지로 넘어가는 메소드.
	 *
	 * @param name        추가할 카테고리 이름
	 * @param parentId    추가할 카테고리의 부모 id
	 * @return 관리 페이지로 넘어감
	 */
	@PostMapping("/admin/category/management")
	public String addCategory(@RequestParam(name = "category-name") String name,
		@RequestParam(name = "parent-name") Long parentId) {
		categoryService.addCategory(name, parentId);
		return "redirect:/admin/category/management";
	}
}
