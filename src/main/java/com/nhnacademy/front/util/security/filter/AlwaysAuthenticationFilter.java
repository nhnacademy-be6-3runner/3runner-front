package com.nhnacademy.front.util.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nhnacademy.front.auth.service.LoginService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlwaysAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager customAuthenticationManager;

	private final LoginService loginService;

	public AlwaysAuthenticationFilter(AuthenticationManager customAuthenticationManager, LoginService loginService) {
		this.customAuthenticationManager = customAuthenticationManager;
		this.loginService = loginService;
		setFilterProcessesUrl("/**");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		Cookie[] cookie = request.getCookies();
		if (cookie != null) {
			for (Cookie c : cookie) {
				if (c.getName().equals("Access")) {
					String token = c.getValue();
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						token, "1234"
					);
					return customAuthenticationManager.authenticate(authToken);
				}
			}
		}

		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		log.info("기본 로그인 성공");
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
		log.info("기본 로그인 실패");
		super.unsuccessfulAuthentication(request, response, failed);

	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		// Access 쿠키가 없으면 인증을 시도하지 않고 다음 필터로 넘어감
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("Access")) {
					return true; // Access 쿠키가 있으면 인증 시도
				}
			}
		}
		return false; // Access 쿠키가 없으면 인증 시도하지 않음
	}
}
