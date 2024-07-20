package com.nhnacademy.front.member.member.controller;

import java.util.List;

import com.nhnacademy.front.purchase.purchase.dto.member.request.UpdateMemberRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.nhnacademy.front.auth.adapter.DormantAdapter;
import com.nhnacademy.front.auth.adapter.LoginAdapter;
import com.nhnacademy.front.auth.dto.request.DormantCodeRequeset;
import com.nhnacademy.front.auth.dto.request.DormantRequest;
import com.nhnacademy.front.auth.service.LoginService;
import com.nhnacademy.front.member.address.dto.response.AddressResponse;
import com.nhnacademy.front.member.address.feign.AddressControllerClient;

import com.nhnacademy.front.member.member.dto.request.CreateMemberRequest;
import com.nhnacademy.front.member.member.dto.request.PasswordCorrectRequest;
import com.nhnacademy.front.member.member.dto.request.UpdatePasswordRequest;

import com.nhnacademy.front.member.member.dto.response.DormantResponse;
import com.nhnacademy.front.member.member.dto.response.UpdateMemberResponse;
import com.nhnacademy.front.member.member.feign.MemberControllerClient;
import com.nhnacademy.front.purchase.purchase.dto.member.response.GetMemberResponse;
import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.front.util.ApiResponse;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberControllerClient memberControllerClient;
	private final AddressControllerClient addressControllerClient;
	private final TokenService tokenService;
	private final LoginService loginService;
	private final LoginAdapter loginAdapter;
	private final DormantAdapter dormantAdapter;

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

	@GetMapping("/member/mypage")
	public String memberMypage() {
		return "mypage/mypage";
	}

	@GetMapping("/member")
	public String myPage(Model model) {
			ApiResponse<GetMemberResponse> memberdetails = memberControllerClient.readById();
			ApiResponse<List<AddressResponse>> addresses = addressControllerClient.readAllAddresses();
			model.addAttribute("member", memberdetails.getBody().getData());
			model.addAttribute("addresses", addresses.getBody().getData());
		return "memberdetail";
	}
	@ResponseBody
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
	@ResponseBody
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
	@ResponseBody
	@PutMapping("/member/password")
	public Boolean updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
		ApiResponse<Void> response = memberControllerClient.updatePassword(updatePasswordRequest);
		return response.getHeader().isSuccessful();
		//요청boolean은 잘들어오
	}
	@ResponseBody
	@PostMapping("/member/password")
	public Boolean isPasswordMatch(@RequestBody PasswordCorrectRequest passwordCorrectRequest) {
		ApiResponse<Void> response = memberControllerClient.isPasswordMatch(passwordCorrectRequest);
		return response.getHeader().isSuccessful();
	}
	@GetMapping("/member/dormant")
	public String dormantForm(Model model,HttpServletRequest request) {
		return "dormant";
	}
	@ResponseBody
	@GetMapping("/member/dormant/resend")
	public boolean resendDormant(HttpSession session) {
		String email = session.getAttribute("email").toString();
		ApiResponse<Void> response = dormantAdapter.resendDormant(email);
		return response.getHeader().isSuccessful();
	}
	@ResponseBody
	@PostMapping("/member/dormant")
	public boolean dormant(@RequestBody DormantCodeRequeset code, HttpSession session,HttpServletRequest servletRequest,HttpServletResponse servletResponse){
		ApiResponse<DormantResponse> response = dormantAdapter.dormantCheck(
			DormantRequest.builder().email(session.getAttribute("email").toString()).code(code.code()).build());
		DormantResponse dormantResponse = response.getBody().getData();
		if(response.getHeader().isSuccessful()){
			String access = dormantResponse.access();//이건 걍 access토큰 값아닝가...

			String refresh = dormantResponse.refresh();

			Cookie cookie1 = new Cookie("Access", access);//이거도 걍 access토큰값넣으면되고
			cookie1.setPath("/");
			servletResponse.addCookie(cookie1);
			Cookie cookie2 = new Cookie("Refresh", refresh);
			cookie2.setPath("/");
			servletResponse.addCookie(cookie2);

			session.removeAttribute("email");
			return true;
		}else {
			return false;
		}

	}
}

