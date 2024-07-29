package com.nhnacademy.front.main.controller;

import com.nhnacademy.front.purchase.mypage.service.MemberMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class HomeController {
	private final MemberMessageService memberMessageService;

	@GetMapping
	public String home(
		@CookieValue(value = "Access", required = false) String access,
		Model model) {
		if (Objects.nonNull(access)) {
			model.addAttribute("unreadMessageCount", memberMessageService.readUnReadedMessage());
		}
		return "main/main";
	}

	@GetMapping("/search")
	public String bookSearch(@RequestParam String keyword, @RequestParam(defaultValue = "0") String page, Model model) {
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);

		return "book/search/book-search";
	}
}