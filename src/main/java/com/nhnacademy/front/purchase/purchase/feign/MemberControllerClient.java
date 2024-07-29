package com.nhnacademy.front.purchase.purchase.feign;

import com.nhnacademy.front.purchase.purchase.dto.member.response.GetMemberResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MemberControllerClient", url = "${feign.client.url}")
public interface MemberControllerClient {
	@GetMapping("/bookstore/members")
	ApiResponse<GetMemberResponse> readById();
}
