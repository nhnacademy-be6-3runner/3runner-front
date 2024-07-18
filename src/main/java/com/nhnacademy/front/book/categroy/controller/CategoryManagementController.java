package com.nhnacademy.front.book.categroy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.book.categroy.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RequestMapping
@Controller
@RequiredArgsConstructor
public class CategoryManagementController {
	private final CategoryService categoryService;

	@GetMapping("/admin/category/management")
	public String categoryManagement(Model model) {
		return "admin/admin_category";
	}

	@GetMapping("/admin/category/delete/{categoryId}")
	public String deleteCategory(@PathVariable long categoryId) {
		categoryService.deleteCategory(categoryId);
		return "redirect:/admin/category/management";
	}

	@PostMapping("/admin/category/management")
	public String addCategory(@RequestParam(name = "category-name") String name,
		@RequestParam(name = "parent-name") Long parentId) {
		categoryService.addCategory(name, parentId);
		return "redirect:/admin/category/management";
	}
}
