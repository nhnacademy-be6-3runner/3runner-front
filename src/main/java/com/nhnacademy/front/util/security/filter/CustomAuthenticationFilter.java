package com.nhnacademy.front.util.security.filter;

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
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager customAuthenticationManager;
	private final LoginService loginService;

	public CustomAuthenticationFilter(AuthenticationManager customAuthenticationManager,
		LoginService loginService) {
		this.customAuthenticationManager = customAuthenticationManager;
		this.loginService = loginService;
		setFilterProcessesUrl("/login/process");

	}

	//TODO 현재 로그인 아이디 비번만 상태일때만
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		LoginRequest loginRequest = null;

		if (Objects.isNull(TokenHolder.getAccessToken())) {

			String email = request.getParameter("email");
			String password = request.getParameter("password");
			loginRequest = new LoginRequest(email, password);

			String message = loginService.getLoginResponse(loginRequest).message();
			if (!message.equals("인증 성공")) {
				if (message.equals("휴면 계정")) {
					throw new DormantException(message);
				}
				throw new LoginException(message);
			}

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
		switch (failed) {
			case DormantException dormantException -> {
				request.getSession().setAttribute("email", request.getParameter("email"));
				response.sendRedirect("/member/dormant");
			}
			case null, default -> {

				request.getSession().setAttribute("errorMessage", "아이디나 비밀번호가 틀립니다.");
				response.sendRedirect("/login");
			}
		}
	}
}