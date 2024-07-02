package com.nhnacademy.front.auth.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhnacademy.front.auth.adapter.LoginAdapter;
import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.dto.response.LoginResponse;
import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.util.ApiResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

	@Autowired
	LoginService loginService;

	/**
	 * login 상태를 체크한 후, 로그인 폼 페이지를 반환한다.
	 * login 되어 있으면 / 로 redirect 한다.
	 *
	 * @return login form view
	 */

	@GetMapping("/login")
	public String loginForm(Model model) {
		if (model.containsAttribute("errorMessage")) {
			String errorMessage = (String) model.getAttribute("errorMessage");
			model.addAttribute("errorMessage", errorMessage);
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
	public String login(@RequestParam String email, @RequestParam String password,
		HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			LoginResponse loginResponse = loginService.getLoginResponse(new LoginRequest(email, password));
		}catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "아이디나 비밀번호가 틀립니다.");
			return "redirect:/login";
		}
		// 쿠키로 저장
		response.addCookie(new Cookie("Access", TokenHolder.getAccessToken()));
		response.addCookie(new Cookie("Refresh", TokenHolder.getRefreshToken()));
		boolean loginStatus = loginService.checkLoginStatus();
		log.warn("login status: {}", loginStatus);

		return "redirect:/";
	}

	/**
	 * 로그아웃 요청을 보낸다.
	 * 쿠키 설정을 위해 request, response 필요
	 *
	 * @param request the request
	 * @param response the response
	 * @return the api response
	 */
	@PostMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		loginService.logout();

		// Cookie 삭제
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("Access")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			if (cookie.getName().equals("Refresh")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}

		return "redirect:/";
	}

	// @PutMapping("/auth/login")
	// public String loginMember(@Valid @RequestBody com.nhnacademy.front.member.address.dto.request.LoginRequest loginRequest) {
	// 	authAdapter.loginMember(loginRequest);
	// 	return "index";//성공하면 기본페이지 실패하면 음...로그인페이지 다시?
	// }
	//로그인할때 꼭 @이메일 되어잇는 인자 받아야한다.

	@GetMapping("/oauth2/callback/payco")
	public String paycoCallback(@RequestParam String code, HttpServletResponse response) {
		//코드까지는 들어온다.

		LoginResponse loginResponse = loginAdapter.handleOAuth2Redirect(code).getBody().getData();//여기까지됌
		response.addCookie(new Cookie("Access", TokenHolder.getAccessToken()));//
		response.addCookie(new Cookie("Refresh", TokenHolder.getRefreshToken()));



		return "index";//메인페이지 반환한다.
	}
}
