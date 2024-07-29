package com.nhnacademy.front.util.security.config;

import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.front.util.JWTUtil;
import com.nhnacademy.front.util.security.CustomUserDetailService;
import com.nhnacademy.front.util.security.filter.AlwaysAuthenticationFilter;
import com.nhnacademy.front.util.security.filter.CustomAuthenticationFilter;
import com.nhnacademy.front.util.security.provider.CustomAuthenticationProvider;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailService userDetailsService;
	private final JWTUtil jwtUtil;
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
		return new ProviderManager(List.of(customAuthenticationProvider));
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable);

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
			new CustomAuthenticationFilter(authenticationManager(), loginService),
			UsernamePasswordAuthenticationFilter.class
		);
		http.addFilterAt(
			new AlwaysAuthenticationFilter(authenticationManager(), loginService, jwtUtil, tokenService),
			UsernamePasswordAuthenticationFilter.class);

		http
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

}
