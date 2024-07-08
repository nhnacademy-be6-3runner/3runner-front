package com.nhnacademy.front.purchase.purchase.feign;

import com.nhnacademy.front.purchase.purchase.dto.member.request.CreateAddressRequest;
import com.nhnacademy.front.purchase.purchase.dto.member.response.AddressResponse;

import com.nhnacademy.front.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "MemberAddressControllerClient", url = "http://${feign.client.url}")
public interface MemberAddressControllerClient {
    @GetMapping("/bookstore/members/addresses")
    ApiResponse<List<AddressResponse>> readAllAddresses();

    @PostMapping("/bookstore/members/addresses")
    ApiResponse<Void> createAddress(@RequestBody @Valid CreateAddressRequest request);

}