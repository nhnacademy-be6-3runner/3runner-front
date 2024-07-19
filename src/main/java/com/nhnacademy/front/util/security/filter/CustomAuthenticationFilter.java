package com.nhnacademy.front.util.security.filter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.auth.dto.request.LoginRequest;
import com.nhnacademy.front.auth.exception.DormantException;
import com.nhnacademy.front.auth.exception.LoginException;
import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.threadlocal.TokenHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// @RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager customAuthenticationManager;
	private final ObjectMapper objectMapper;
	private final LoginService loginService;

	public CustomAuthenticationFilter(AuthenticationManager customAuthenticationManager,
		ObjectMapper objectMapper, LoginService loginService) {
		this.customAuthenticationManager = customAuthenticationManager;
		this.objectMapper = objectMapper;
		this.loginService = loginService;
		setFilterProcessesUrl("/login/process");

	}

	//TODO 현재 로그인 아이디 비번만 상태일때만
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		LoginRequest loginRequest = null;

		// Cookie[] cookie = request.getCookies();
		// if (Objects.nonNull(cookie)) {
		// 	for (Cookie c : cookie) {
		// 		if (c.getName().equals("Access")) {
		// 			String token = c.getValue();
		// 			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
		// 				token, "1234"
		// 			);
		// 			return customAuthenticationManager.authenticate(authToken);
		// 		}
		// 	}
		// }
		//TODO 여기 확인
		if (Objects.isNull(TokenHolder.getAccessToken())) {

			String email = request.getParameter("email");
			String password = request.getParameter("password");
			loginRequest = new LoginRequest(email, password);
			//여기서 토큰 생성
			String message = loginService.getLoginResponse(loginRequest).message();
			if (!message.equals("인증 성공")) {
				 if(message.equals("휴면 계정")) {
				 	throw new DormantException(message);
				 }	//인증은 성공했는데 휴면 상태일 경우
				throw new LoginException(message);
			}//인증자체가 실패했을 경우

		}
		String token = TokenHolder.getAccessToken();
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
			token, "1234"
		);
		return customAuthenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		log.info("로그인 성공");
		Cookie cookie1 = new Cookie("Access", TokenHolder.getAccessToken());
		cookie1.setPath("/");
		response.addCookie(cookie1);
		Cookie cookie2 = new Cookie("Refresh", TokenHolder.getRefreshToken());
		cookie2.setPath("/");
		cookie2.setMaxAge(60 * 60 * 24 * 7);
		response.addCookie(cookie2);
		SecurityContextHolder.getContext().setAuthentication(authResult);
		super.successfulAuthentication(request, response, chain, authResult);

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
			if(failed instanceof DormantException) {
				request.getSession().setAttribute("email",request.getParameter("email"));
				response.sendRedirect("/member/dormant");

			}else {

				request.getSession().setAttribute("errorMessage", "아이디나 비밀번호가 틀립니다.");
				response.sendRedirect("/login");

			}
	}
}