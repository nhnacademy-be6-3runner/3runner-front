package com.nhnacademy.front.member.address.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import com.nhnacademy.front.member.address.dto.request.CreateAddressRequest;
import com.nhnacademy.front.member.address.dto.request.UpdateAddressRequest;

import com.nhnacademy.front.member.address.feign.AddressControllerClient;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class AddressController {
	private final AddressControllerClient addressControllerClient;
	@GetMapping("/api/address/create")
	public String registerForm() {
		return "addresscreate";
	}//등록 폼으로 간다.
	@PostMapping("/api/address/create")
	public String createAddress(@RequestBody(required = true) CreateAddressRequest createAddressRequest,@RequestHeader("member-id")Long memberId){
		//addressControllerClient.createAddress(createAddressRequest,memberId);
		return "/api/address";
	}//전체 address로 간다.
	@GetMapping("/api/address/read")
	public String readAllAddress(@RequestHeader(name = "member-id",required = false)Long memberId) {
		//addressControllerClient.readAllAddresses(memberId);
		return "address";
	}//전체 address창으로 반환해야할듯
	@PutMapping("/api/address/update")
	public String updateAddress(@RequestBody(required = true) UpdateAddressRequest updateAddressRequest,@RequestHeader("member-id")Long memberId) {
		//addressControllerClient.updateAddress(updateAddressRequest,memberId);
		return "address";
	}//여기도 mypage창에 주소해서 해야할듯
	@DeleteMapping("/api/address/delete")
	public String deleteAddress(@RequestHeader("member-id")Long memberId) {
		//addressControllerClient.deleteAddress(memberId);
		return "address";
	}//먼가 전체 addresslist를 보여주는 창을 만들어야할거같다. 아무래도 mypage창?
}

