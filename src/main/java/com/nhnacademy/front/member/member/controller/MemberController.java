package com.nhnacademy.front.member.member.controller;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.member.address.dto.response.AddressResponse;
import com.nhnacademy.front.member.address.feign.AddressControllerClient;
import com.nhnacademy.front.member.member.dto.request.CreateMemberRequest;
import com.nhnacademy.front.member.member.dto.request.PasswordCorrectRequest;
import com.nhnacademy.front.member.member.dto.request.UpdateMemberRequest;
import com.nhnacademy.front.member.member.dto.request.UpdatePasswordRequest;
import com.nhnacademy.front.member.member.dto.response.GetMemberResponse;
import com.nhnacademy.front.member.member.dto.response.UpdateMemberResponse;
import com.nhnacademy.front.member.member.feign.MemberControllerClient;
import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.util.ApiResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberControllerClient memberControllerClient;
	private final AddressControllerClient addressControllerClient;
	private final TokenService tokenService;
	private final LoginService loginService;

	@GetMapping("/member/createForm")
	public String createSigninPage() {
		return "signin";
	}//등록페이지

	@PostMapping("/member")
	public String signin(@Valid @RequestBody CreateMemberRequest createMemberRequest) {
		System.out.println(createMemberRequest);
		ApiResponse<Void> result = memberControllerClient.createMembers(createMemberRequest);
		if(!result.getHeader().isSuccessful()){
			return "redirect:/member/createForm";
		}

		return "test-main";
	}//등록 요청보낸후 메인페이지 반환

	@GetMapping("/member/mypageForm")
	public String myPage() {

		return "mypage";
	}

	@GetMapping("/member")
	public String myPage(Model model) {
			ApiResponse<GetMemberResponse> memberdetails = memberControllerClient.readById();
			ApiResponse<List<AddressResponse>> addresses = addressControllerClient.readAllAddresses();
			model.addAttribute("member", memberdetails.getBody().getData());
			model.addAttribute("addresses", addresses.getBody().getData());
		return "memberdetail";
	}
	@DeleteMapping("/member")
	public Boolean deleteMember(HttpServletRequest request, HttpServletResponse response) {
		ApiResponse<Void> result = memberControllerClient.deleteMember();

		try{
			loginService.logout();
		// Cookie 삭제
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals("Access")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			if (cookie.getName().equals("Refresh")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		}catch (Exception e) {
			return false;
		}
		return result.getHeader().isSuccessful();
	}//삭제 후 메인 페이지 반환
	@PutMapping("/member")
	public Boolean updateMember(@Valid @RequestBody UpdateMemberRequest updateMemberRequest) {
		ApiResponse<UpdateMemberResponse> response = memberControllerClient.updateMembers(updateMemberRequest);
		return response.getHeader().isSuccessful();
		//페이지 리로드 된다.
	}
	@ResponseBody
	@GetMapping("/member/email")
	public ApiResponse<Boolean> checkEmailExists(@RequestParam String email) {
		// 이메일 존재 여부 확인 로직
		ApiResponse<Boolean> result = memberControllerClient.emailExists(email);
		return result;
	}
	@PutMapping("/member/password")
	public Boolean updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
		ApiResponse<Void> response = memberControllerClient.updatePassword(updatePasswordRequest);
		return response.getHeader().isSuccessful();
		//요청boolean은 잘들어오
	}
	@PostMapping("/member/password")
	public Boolean isPasswordMatch(@RequestBody PasswordCorrectRequest passwordCorrectRequest) {
		ApiResponse<Void> response = memberControllerClient.isPasswordMatch(passwordCorrectRequest);
		return response.getHeader().isSuccessful();
	}
}

