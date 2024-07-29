package com.nhnacademy.front.book.categroy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 카테고리 관련 컨트롤러
 *
 * @author 한민기
 */
@Controller
public class CategoryController {

	/**
	 * 카테고리에 관련된 책을 보여주기위한 url로 넘어가는 메소드.
	 *
	 * @param categoryId 카테고리 id
	 * @param model model 카테고리 id
	 * @return 카테고리 책 화면으로
	 */
	@GetMapping("/categories/books")
	public String categoriesBooks(@RequestParam(defaultValue = "1") Long categoryId, Model model) {
		model.addAttribute("categoryId", categoryId);
		return "book/list/book-category-list";
	}

}
