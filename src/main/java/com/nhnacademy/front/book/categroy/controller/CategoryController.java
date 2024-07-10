package com.nhnacademy.front.book.categroy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

	@GetMapping("/categories/books")
	public String categoriesBooks(@RequestParam(defaultValue = "1") Long categoryId, Model model) {
		model.addAttribute("categoryId", categoryId);
		return "book/list/book-category-list";
	}

}
