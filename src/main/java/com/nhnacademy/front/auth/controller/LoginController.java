package com.nhnacademy.front.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.front.auth.adapter.LoginAdapter;
import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;

@Controller
public class LoginController {
	@Autowired
	LoginAdapter loginAdapter;

	@GetMapping("/login")
	public String loginForm() {
		return "login-form";
	}

	@PostMapping("/login")
	@ResponseBody
	public LoginResponse login(@RequestParam String email, @RequestParam String password) {
		return loginAdapter.login(new LoginRequest(email, password));
	}

}
