package com.nhnacademy.front.util.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.front.util.CookieUtil;
import com.nhnacademy.front.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlwaysAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager customAuthenticationManager;

	private final TokenService tokenService;
	private final JWTUtil jwtUtil;
	private final LoginService loginService;

	public AlwaysAuthenticationFilter(AuthenticationManager customAuthenticationManager, LoginService loginService,
		JWTUtil jwtUtil, TokenService tokenService) {
		this.customAuthenticationManager = customAuthenticationManager;
		this.loginService = loginService;
		this.jwtUtil = jwtUtil;
		this.tokenService = tokenService;
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
					if (jwtUtil.isExpired(token)) {
						String refreshToken = findRefreshToken(request, response);

						token = tokenService.requestNewAccessToken(refreshToken);

						c.setMaxAge(0);
						response.addCookie(c);

						Cookie newAccessTokenCookie = CookieUtil.createCookie("Access", token);
						response.addCookie(newAccessTokenCookie);
						log.error("재발급 쿠키에 설정 - access token: {}", token);

						// 새로운 refresh cookie 추가
						Cookie newRefreshTokenCookie = CookieUtil.createCookie("Refresh", TokenHolder.getRefreshToken(),
							7 * 24 * 60 * 60);

						response.addCookie(newRefreshTokenCookie);
						log.error("재발급 쿠키에 설정 - refresh token: {}", TokenHolder.getRefreshToken());

					}
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						token, "1234"
					);
					TokenHolder.setAccessToken(token);
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

	private String findRefreshToken(HttpServletRequest request, HttpServletResponse response) {
		// Access Token 검증, 만료 되었으면 REFRESH TOKEN 전송
		Cookie[] cookies = request.getCookies();
		String refreshToken = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("Refresh")) {
					refreshToken = cookie.getValue();
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					return refreshToken;
				}
			}
		}
		return refreshToken;
	}

}
