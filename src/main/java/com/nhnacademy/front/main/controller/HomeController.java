package com.nhnacademy.front.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	@GetMapping
	public String home() {
		return "main/main";
	}

	@GetMapping("/search")
	public String bookSearch(@RequestParam String keyword, @RequestParam(defaultValue = "0") String page, Model model) {
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);

		return "book/search/book-search";
	}
}