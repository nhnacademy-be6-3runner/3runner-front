package com.nhnacademy.front.member.member.feign;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.nhnacademy.front.member.member.dto.request.CreateMemberRequest;
import com.nhnacademy.front.member.member.dto.request.UpdateMemberRequest;
import com.nhnacademy.front.member.member.dto.response.GetMemberResponse;
import com.nhnacademy.front.member.member.dto.response.UpdateMemberResponse;
import com.nhnacademy.util.ApiResponse;

import jakarta.validation.Valid;
@FeignClient(name = "memberControllerClient",url = "http://localhost:8080")
public interface MemberControllerClient {
	@GetMapping("/bookstore/members")
	ApiResponse<GetMemberResponse> readById();
	@PostMapping("/bookstore/members")
	ApiResponse<Void> createMembers(@Valid @RequestBody CreateMemberRequest createMemberRequest);
	@PutMapping("/bookstore/members")
	ApiResponse<UpdateMemberResponse> updateMembers(@Valid @RequestBody UpdateMemberRequest updateMemberRequest);
	@DeleteMapping("/bookstore/members")
	ApiResponse<Void> deleteMember();
}

