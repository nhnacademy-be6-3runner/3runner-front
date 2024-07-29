package com.nhnacademy.front.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	/**
	 * 관리자 메인 화면.
	 *
	 * @return 관리자 메인 화면
	 */
	@GetMapping
	public String adminIndex() {
		return "admin/admin_main";
	}
}
