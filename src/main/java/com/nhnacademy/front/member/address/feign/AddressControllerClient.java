package com.nhnacademy.front.member.address.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.front.member.address.dto.request.UpdateAddressRequest;
import com.nhnacademy.front.member.address.dto.response.AddressResponse;
import com.nhnacademy.front.member.address.dto.response.UpdateAddressResponse;
import com.nhnacademy.front.purchase.purchase.dto.member.request.CreateAddressRequest;
import com.nhnacademy.front.util.ApiResponse;

import jakarta.validation.Valid;

@FeignClient(name = "addressControllerClient", url = "${feign.client.url}")
public interface AddressControllerClient {
	@PostMapping("/bookstore/members/addresses")
	ApiResponse<Void> createAddress(@Valid @RequestBody CreateAddressRequest request);

	@GetMapping("/bookstore/members/addresses")
	ApiResponse<List<AddressResponse>> readAllAddresses();

	@PutMapping("/bookstore/members/addresses/{addressId}")
	ApiResponse<UpdateAddressResponse> updateAddress(@Valid @RequestBody UpdateAddressRequest request,
		@PathVariable(name = "addressId") Long addressId);

	@DeleteMapping("/bookstore/members/addresses/{addressId}")
	ApiResponse<Void> deleteAddress(@PathVariable(name = "addressId") Long addressId);
}

