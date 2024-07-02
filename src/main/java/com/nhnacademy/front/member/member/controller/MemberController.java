package com.nhnacademy.front.member.member.controller;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.nhnacademy.front.member.address.dto.response.AddressResponse;
import com.nhnacademy.front.member.address.feign.AddressControllerClient;
import com.nhnacademy.front.member.member.dto.request.CreateMemberRequest;
import com.nhnacademy.front.member.member.dto.request.UpdateMemberRequest;
import com.nhnacademy.front.member.member.dto.response.GetMemberResponse;
import com.nhnacademy.front.member.member.feign.MemberControllerClient;
import com.nhnacademy.front.threadlocal.TokenHolder;
import com.nhnacademy.front.token.service.TokenService;
import com.nhnacademy.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberControllerClient memberControllerClient;
	private final AddressControllerClient addressControllerClient;
	private final TokenService tokenService;

	@GetMapping("/member/createForm")
	public String createSigninPage() {
		return "signin";
	}//등록페이지

	@PostMapping("/member")
	public String signin(@Valid @RequestBody CreateMemberRequest createMemberRequest) {
		memberControllerClient.createMembers(createMemberRequest);

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
	public String deleteMember() {
		memberControllerClient.deleteMember();

		return "test-main";
	}//삭제 후 메인 페이지 반환
	@PutMapping("/member")
	public String updateMember(@Valid @RequestBody UpdateMemberRequest updateMemberRequest) {
		memberControllerClient.updateMembers(updateMemberRequest);
		return null;
		//페이지 리로드 된다.
	}
}

