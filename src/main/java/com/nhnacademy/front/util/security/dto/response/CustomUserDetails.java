package com.nhnacademy.front.util.security.dto.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nhnacademy.front.auth.dto.response.MemberAuthResponse;

/**
 * 커스텀 유저 디테일 클래스
 *
 * @author 한민기
 */
public class CustomUserDetails implements UserDetails {
	private final MemberAuthResponse memberAuthResponse;

	public CustomUserDetails(MemberAuthResponse memberAuthResponse) {
		this.memberAuthResponse = memberAuthResponse;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();

		List<String> authorities = memberAuthResponse.auth();

		for (String authority : authorities) {
			collection.add(new SimpleGrantedAuthority(authority));
		}
		return collection;
	}

	@Override
	public String getPassword() {
		return memberAuthResponse.email();
	}

	@Override
	public String getUsername() {
		return "1234";
	}

	public Long getMemberId() {
		return memberAuthResponse.memberId();
	}
}
