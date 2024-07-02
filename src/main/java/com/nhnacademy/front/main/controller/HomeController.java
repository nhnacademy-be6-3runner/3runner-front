package com.nhnacademy.front.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping
	public String home() {

		return "test-main";
	}
}
