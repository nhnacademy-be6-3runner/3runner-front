package com.nhnacademy.front.util.security.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.front.util.JWTUtil;
import com.nhnacademy.front.util.security.CustomUserDetailService;
import com.nhnacademy.front.util.security.filter.AlwaysAuthenticationFilter;
import com.nhnacademy.front.util.security.filter.CustomAuthenticationFilter;
import com.nhnacademy.front.util.security.provider.CustomAuthenticationProvider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailService userDetailsService;
	private final ObjectMapper objectMapper;
	private JWTUtil jwtUtil;
	private final LoginService loginService;
	private final TokenService tokenService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider(
			userDetailsService, passwordEncoder());
		return new ProviderManager(Arrays.asList(customAuthenticationProvider));
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable);
		// http
		// 	.formLogin(AbstractHttpConfigurer::disable);
		// http
		// 	.httpBasic(AbstractHttpConfigurer::disable);

		http.authorizeHttpRequests(
			(authorizeRequests) -> {
				authorizeRequests
					.requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
					.requestMatchers("/publisher/**").hasAnyAuthority("ADMIN", "PUBLISHER")
					.requestMatchers("/member/mypageForm/**").authenticated()
					.anyRequest().permitAll();
			}
		);

		http.formLogin((formLogin) -> {
			formLogin.loginPage("/login");
			formLogin.loginProcessingUrl("/login/process");
			formLogin.usernameParameter("email");
			formLogin.passwordParameter("password");
			formLogin.defaultSuccessUrl("/");
		});

		http.logout((logoutConfig) -> {
			logoutConfig.logoutSuccessUrl("/");
			logoutConfig.deleteCookies("Access", "Refresh");
			logoutConfig.invalidateHttpSession(true)
				.logoutUrl("/logout");
		});

		http.exceptionHandling(exceptionHandling ->
			exceptionHandling
				.accessDeniedHandler((request, response, accessDeniedException) -> {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.setContentType("application/json; charset=utf-8");
					response.getWriter().write("접근이 제한 되었습니다.");
				})
		);

		http.addFilterAt(
			new CustomAuthenticationFilter(authenticationManager(), objectMapper, loginService),
			UsernamePasswordAuthenticationFilter.class
		);
		http.addFilterAt(
			new AlwaysAuthenticationFilter(authenticationManager(), loginService, jwtUtil, tokenService),
			UsernamePasswordAuthenticationFilter.class);

		http
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// http.addFilterBefore(new OncePerRequestFilter() {
		// 	@Override
		// 	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		// 		FilterChain filterChain) throws ServletException, IOException {
		// 		request.getSession().invalidate(); // Invalidate any existing session
		// 		filterChain.doFilter(request, response);
		// 	}
		// }, SecurityContextPersistenceFilter.class);

		return http.build();
	}

}
