package com.nhnacademy.front.member.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.member.address.dto.request.LoginRequest;
import com.nhnacademy.front.member.address.dto.response.AddressResponse;
import com.nhnacademy.front.member.address.feign.AddressControllerClient;
import com.nhnacademy.front.member.member.dto.request.CreateMemberRequest;
import com.nhnacademy.front.member.member.dto.request.UpdateMemberRequest;
import com.nhnacademy.front.member.member.dto.response.GetMemberResponse;
import com.nhnacademy.front.member.member.feign.MemberControllerClient;
import com.nhnacademy.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberControllerClient memberControllerClient;
	private final AddressControllerClient addressControllerClient;
	@GetMapping("/api/signin")
	public String createSigninPage() {
		return "signin";
	}//등록페이지
	@PostMapping("/api/signin")
	public String signin(@Valid @RequestBody CreateMemberRequest createMemberRequest) {
		memberControllerClient.createMembers(createMemberRequest);
		return "index";
	}//등록 요청보낸후 메인페이지 반환

	@GetMapping("/api/mypage")
	public String myPage() {
		return "mypage";
	}

	@GetMapping("/api/memberdetail")
	public String myPage(@RequestHeader("member-id") Long memberId, Model model) {
			ApiResponse<GetMemberResponse> memberdetails = memberControllerClient.readById(memberId);
			//ApiResponse<List<AddressResponse>> addresses = addressControllerClient.readAllAddresses(memberId);
			model.addAttribute("member", memberdetails.getBody().getData());
			//model.addAttribute("addresses", addresses.getBody().getData());
		return "memberdetail";
	}
	@DeleteMapping("/api/member/delete")
	public String deleteMember(@RequestParam Long id) {
		memberControllerClient.deleteMember(id);
		return "mypage";
	}//삭제 후 마이페이지 반환
	@PutMapping("/api/member/update")
	public String updateMember(@RequestHeader(name = "memberid")Long memberId,@Valid @RequestBody UpdateMemberRequest updateMemberRequest) {
		memberControllerClient.updateMembers(memberId,updateMemberRequest);
		return "mypage";
	}
	@PutMapping("/api/auth/login")
	public String loginMember(@Valid @RequestBody LoginRequest loginRequest) {
		memberControllerClient.loginMember(loginRequest);
		return "index";//성공하면 기본페이지 실패하면 음...로그인페이지 다시?
	}
	//로그인할때 꼭 @이메일 되어잇는 인자 받아야한다.
}

