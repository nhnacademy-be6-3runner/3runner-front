package com.nhnacademy.front.purchase.purchase.feign;

import com.nhnacademy.front.purchase.purchase.dto.member.request.CreateAddressRequest;
import com.nhnacademy.front.purchase.purchase.dto.member.response.AddressResponse;
import com.nhnacademy.front.util.ApiResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MemberAddressControllerClient", url = "${feign.client.url}")
public interface MemberAddressControllerClient {
	@GetMapping("/bookstore/members/addresses")
	ApiResponse<List<AddressResponse>> readAllAddresses();

	@PostMapping("/bookstore/members/addresses")
	ApiResponse<Long> createAddress(@RequestBody CreateAddressRequest request);
}
