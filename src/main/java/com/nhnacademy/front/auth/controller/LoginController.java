package com.nhnacademy.front.auth.controller;

import com.nhnacademy.front.auth.adapter.LoginAdapter;
import com.nhnacademy.front.auth.dto.response.LoginResponse;
import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.threadlocal.TokenHolder;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Login, Logout 관련 컨트롤러
 *
 * @author 오연수
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginAdapter loginAdapter;

	private final LoginService loginService;

	/**
	 * login 상태를 체크한 후, 로그인 폼 페이지를 반환한다.
	 * login 되어 있으면 / 로 redirect 한다.
	 *
	 * @return login form view
	 */
	@GetMapping("/login")
	public String loginForm(Model model, HttpSession session) {
		boolean loginStatus = loginService.checkLoginStatus();
		log.warn("login status: {}", loginStatus);

		if (loginStatus) {
			return "redirect:/";
		} else {
			String errorMessage = (String)session.getAttribute("errorMessage");
			if (errorMessage != null) {
				model.addAttribute("errorMessage", errorMessage);
				session.removeAttribute("errorMessage");
			}
		}
		return "login-form";
	}

	@GetMapping("/oauth2/callback/payco")
	public String paycoCallback(@RequestParam String code, HttpServletResponse response,
		HttpServletRequest request) {
		try {
			LoginResponse loginResponse = loginAdapter.handleOAuth2Redirect(code).getBody().getData();
			if (loginResponse.message().equals("일반 회원 이메일인데 페이코 접속")) {
				request.getSession().setAttribute("errorMessage", "일반 회원 이메일로 페이코 접속을 시도하였습니다.");
				return "redirect:/login";
			}
			Cookie cookie1 = new Cookie("Access", TokenHolder.getAccessToken());
			cookie1.setPath("/");
			Cookie cookie2 = new Cookie("Refresh", TokenHolder.getRefreshToken());
			cookie2.setPath("/");
			response.addCookie(cookie1);
			response.addCookie(cookie2);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return "redirect:/";
	}
}
