package com.nhnacademy.front.auth.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.front.auth.adapter.LoginAdapter;
import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;

/**
 * Login, Logout 관련 컨트롤러
 *
 * @author 오연수
 */
@Slf4j
@Controller
public class LoginController {
	@Autowired
	LoginAdapter loginAdapter;

	/**
	 * 로그인 폼 페이지를 반환한다.
	 *
	 * @return login form view
	 */
	@GetMapping("/api/login")
	public String loginForm() {
		return "login-form";
	}

	/**
	 * 로그인 요청을 보낸다.
	 *
	 * @param email 이메일
	 * @param password 패스워드
	 * @param response 헤더로 액세스 토큰 값을 가져오기 위한 응답
	 * @return 로그인 응답 (일단 토큰 값) - 추후 main view 로 리다이렉트
	 */
	@PostMapping("/api/login")
	@ResponseBody
	public LoginResponse login(@RequestParam @Email String email, @RequestParam String password,
		HttpServletResponse response) {
		ResponseEntity<LoginResponse> loginResponse = loginAdapter.login(new LoginRequest(email, password));

		String accessToken = loginResponse.getHeaders().getFirst("Authorization");

		if (Objects.nonNull(accessToken)) {
			log.warn("Access token {}", accessToken);
			String[] tokens = accessToken.split(" ");
			Cookie cookie = new Cookie("Access", tokens[1].replace("Bearer ", ""));
			response.addCookie(cookie);
		}

		return loginResponse.getBody();
	}

}
