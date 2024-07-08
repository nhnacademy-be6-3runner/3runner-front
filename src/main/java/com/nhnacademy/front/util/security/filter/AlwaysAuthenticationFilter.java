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
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
			null, "1234"
		);
		return customAuthenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		log.info("기본 로그인 성공");
		SecurityContextHolder.getContext().setAuthentication(authResult);
		super.successfulAuthentication(request, response, chain, authResult);

	}

}
