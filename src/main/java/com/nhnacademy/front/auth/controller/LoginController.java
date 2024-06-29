package com.nhnacademy.front.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;
import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.util.ApiResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
	LoginService loginService;

	/**
	 * login 상태를 체크한 후, 로그인 폼 페이지를 반환한다.
	 * login 되어 있으면 / 로 redirect 한다.
	 *
	 * @return login form view
	 */
	@GetMapping("/login")
	public String loginForm() {
		boolean loginStatus = loginService.checkLoginStatus();
		log.warn("login status: {}", loginStatus);

		if (loginStatus) {
			return "redirect:/";
		}
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
	@PostMapping("/login")
	@ResponseBody
	public ApiResponse<LoginResponse> login(@RequestParam String email, @RequestParam String password,
		HttpServletResponse response) {
		LoginResponse loginResponse = loginService.getLoginResponse(new LoginRequest(email, password));

		// String encodedAccess = URLEncoder.encode(TokenHolder.getAccessToken(), "UTF-8");
		// String encodedRefresh = URLEncoder.encode(TokenHolder.getRefreshToken(), "UTF-8");

		// response.addHeader("Authorization", TokenHolder.getAccessToken());
		// response.addHeader("Refresh", TokenHolder.getRefreshToken());

		// 쿠키로 저장
		response.addCookie(new Cookie("Access", TokenHolder.getAccessToken()));
		response.addCookie(new Cookie("Refresh", TokenHolder.getRefreshToken()));

		return ApiResponse.success(loginResponse);
	}

}
