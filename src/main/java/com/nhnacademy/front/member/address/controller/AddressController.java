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
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public Boolean createAddress(@RequestBody(required = true) CreateAddressRequest createAddressRequest){
		ApiResponse<Void> apiResponse= addressControllerClient.createAddress(createAddressRequest);//들어오긴들어옴
		return apiResponse.getHeader().isSuccessful();
	}//리로드 된다.
	@GetMapping("/member/address")
	public List<AddressResponse> readAllAddress(Model model) {
		List<AddressResponse> addresses = addressControllerClient.readAllAddresses().getBody().getData();
		model.addAttribute("addresses", addresses);
		return addresses;
	}//전체 address창으로 반환해야할듯
	@PutMapping("/member/address/{addressId}")
	public String updateAddress(@RequestBody(required = true) UpdateAddressRequest updateAddressRequest,@PathVariable(name = "addressId")Long addressId) {
		addressControllerClient.updateAddress(updateAddressRequest,addressId);
		return null;
		//페이지 리로드 된다.
	}//여기도 mypage창에 주소해서 해야할듯
	@DeleteMapping("/member/address/{addressId}")
	public Boolean deleteAddress(@PathVariable("addressId")Long addressId) {
		System.out.println("HERE");

		ApiResponse<Void> result = addressControllerClient.deleteAddress(addressId);

		return result.getHeader().isSuccessful();
	}//다시 페이지 리로드 된다.
}

