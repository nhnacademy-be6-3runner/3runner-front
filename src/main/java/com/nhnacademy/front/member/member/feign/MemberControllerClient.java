package com.nhnacademy.front.member.member.feign;

import com.nhnacademy.front.purchase.purchase.dto.member.request.UpdateMemberRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.member.member.dto.request.CreateMemberRequest;
import com.nhnacademy.front.member.member.dto.request.PasswordCorrectRequest;
import com.nhnacademy.front.member.member.dto.request.UpdatePasswordRequest;
import com.nhnacademy.front.member.member.dto.response.UpdateMemberResponse;
import com.nhnacademy.front.purchase.purchase.dto.member.response.GetMemberResponse;
import com.nhnacademy.front.util.ApiResponse;

import jakarta.validation.Valid;

@FeignClient(name = "memberControllerClient", url = "${feign.client.url}")
public interface MemberControllerClient {
	@GetMapping("/bookstore/members")
	ApiResponse<GetMemberResponse> readById();

	@PostMapping("/bookstore/members")
	ApiResponse<Long> createMembers(@RequestBody CreateMemberRequest createMemberRequest);

	@PutMapping("/bookstore/members")
	ApiResponse<UpdateMemberResponse> updateMembers(@Valid @RequestBody UpdateMemberRequest updateMemberRequest);

	@DeleteMapping("/bookstore/members")
	ApiResponse<Void> deleteMember();

	@GetMapping("/bookstore/members/email")
	ApiResponse<Boolean> emailExists(@RequestParam String email);

	@PutMapping("/bookstore/members/password")
	ApiResponse<Void> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest);

	@PostMapping("/bookstore/members/password")
	ApiResponse<Void> isPasswordMatch(@RequestBody PasswordCorrectRequest request);

}

