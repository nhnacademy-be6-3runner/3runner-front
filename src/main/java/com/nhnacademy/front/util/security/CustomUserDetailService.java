package com.nhnacademy.front.util.security;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhnacademy.front.auth.dto.response.MemberAuthResponse;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.front.util.security.dto.response.CustomUserDetails;
import com.nhnacademy.front.util.security.feign.SecurityLoginAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final SecurityLoginAdapter securityLoginAdapter;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApiResponse<MemberAuthResponse> response = null;
		// loginService.getLoginResponse(LoginRequest.builder().build())

		if (username.equals("")) {
			return new CustomUserDetails(MemberAuthResponse.builder().auth(List.of("NONMEMBER")).build());
		}
		try {
			response = securityLoginAdapter.memberLogin(username);
			// response = securityLoginAdapter.memberLoginId(MemberAuthRequest.builder().email(username).build());
		} catch (Exception e) {
			throw new UsernameNotFoundException("이메일로 멤버를 찾을 수 없다.");
		}
		// MemberAuthResponse memberAuthResponse = response.getBody().getData();
		// InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		// return manager.createUser(User.withUsername(
		// 		memberAuthResponse.email())
		// 	.roles(memberAuthResponse.auth().getFirst()).build()
		// );

		return new CustomUserDetails(response.getBody().getData());
	}

}
