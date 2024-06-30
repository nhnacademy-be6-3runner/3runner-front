package com.nhnacademy.front.member.address.controller;

import java.util.List;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import com.nhnacademy.front.member.address.dto.request.CreateAddressRequest;
import com.nhnacademy.front.member.address.dto.request.UpdateAddressRequest;

import com.nhnacademy.front.member.address.dto.response.AddressResponse;
import com.nhnacademy.front.member.address.feign.AddressControllerClient;
import com.nhnacademy.util.ApiResponse;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
public class AddressController {
	private final AddressControllerClient addressControllerClient;
	@GetMapping("/member/address/creatForm")
	public String registerForm() {
		return "addresscreate";
	}//등록 폼으로 간다.
	@PostMapping("/member/address")
	public String createAddress(@RequestBody(required = true) CreateAddressRequest createAddressRequest){
		addressControllerClient.createAddress(createAddressRequest);
		return null;
	}//리로드 된다.
	@GetMapping("/member/address")
	public String readAllAddress(Model model) {
		List<AddressResponse> addresses = addressControllerClient.readAllAddresses().getBody().getData();
		model.addAttribute("addresses", addresses);
		return "address";
	}//전체 address창으로 반환해야할듯
	@PutMapping("/member/address/{addressId}")
	public String updateAddress(@RequestBody(required = true) UpdateAddressRequest updateAddressRequest,@PathVariable(name = "addressId")Long addressId) {
		addressControllerClient.updateAddress(updateAddressRequest,addressId);
		return null;
		//페이지 리로드 된다.
	}//여기도 mypage창에 주소해서 해야할듯
	@DeleteMapping("/member/address/{addressId}")
	public String deleteAddress(@PathVariable("addressId")Long addressId) {
		addressControllerClient.deleteAddress(addressId);
		return null;
	}//다시 페이지 리로드 된다.
}

