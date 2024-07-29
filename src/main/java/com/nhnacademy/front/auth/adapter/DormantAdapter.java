package com.nhnacademy.front.auth.adapter;

import com.nhnacademy.front.auth.dto.request.DormantRequest;
import com.nhnacademy.front.member.member.dto.response.DormantResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 유지아
 * 휴먼 관련 feignClient
 */
@FeignClient(value = "dormant-api", url = "${feign.client.url}")
public interface DormantAdapter {
	/**
	 * 휴먼.
	 *
	 * @param email 휴면 계정의 이메일
	 * @return 응답
	 */
	@PostMapping("/auth/dormant/resend")
	ApiResponse<Void> resendDormant(@RequestBody String email);

	@PostMapping("/auth/dormant")
	ApiResponse<DormantResponse> dormantCheck(@RequestBody DormantRequest loginRequest);

}

