package com.nhnacademy.bookstore.member.memberAuth.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.bookstore.entity.auth.Auth;
import com.nhnacademy.bookstore.entity.member.Member;
import com.nhnacademy.bookstore.member.member.service.MemberService;
import com.nhnacademy.bookstore.member.memberAuth.dto.request.MemberAuthRequest;
import com.nhnacademy.bookstore.member.memberAuth.dto.response.MemberAuthResponse;
import com.nhnacademy.bookstore.member.memberAuth.service.MemberAuthService;

@RestController()
public class MemberAuthController {
	private final MemberService memberService;
	private final MemberAuthService memberAuthService;

	public MemberAuthController(MemberService memberService, MemberAuthService memberAuthService) {
		this.memberService = memberService;
		this.memberAuthService = memberAuthService;
	}

	@PostMapping("/bookstore/login")
	public MemberAuthResponse getMemberAuth(@RequestBody MemberAuthRequest memberAuthRequest) {
		String email = memberAuthRequest.email();

		Member member = memberService.readByEmail(memberAuthRequest.email());
		if (Objects.nonNull(member)) {
			List<Auth> authList = memberAuthService.readAllAuths(member.getId());
			List<String> authStrList = authList.stream().map(Auth::getName).toList();

			return new MemberAuthResponse(email, member.getPassword(), authStrList, member.getId());
		}
		return null;
	}
}
