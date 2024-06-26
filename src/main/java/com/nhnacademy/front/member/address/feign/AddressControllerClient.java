package com.nhnacademy.front.member.address.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


import com.nhnacademy.front.member.address.dto.request.CreateAddressRequest;
import com.nhnacademy.front.member.address.dto.request.UpdateAddressRequest;
import com.nhnacademy.front.member.address.dto.response.AddressResponse;
import com.nhnacademy.front.member.address.dto.response.UpdateAddressResponse;


@FeignClient(name = "addressControllerClient",url = "http://localhost:8080")
public interface AddressControllerClient {
	@PostMapping("/bookstore/members/addresses")
	ApiResponse<List<AddressResponse>> createAddress(@Valid @RequestBody CreateAddressRequest request, @RequestHeader("member-id") Long memberId);
	@GetMapping("/bookstore/members/addresses")
	ApiResponse<List<AddressResponse>> readAllAddresses(@RequestHeader("member-id") Long memberId);
	@PutMapping("/bookstore/members/addresses")
	ApiResponse<UpdateAddressResponse> updateAddress(@Valid @RequestBody UpdateAddressRequest request, @RequestHeader("member-id") Long memberId);
	@DeleteMapping("/bookstore/members/addresses")
	ApiResponse<Void> deleteAddress(@RequestHeader("member-id") Long memberId);
}

