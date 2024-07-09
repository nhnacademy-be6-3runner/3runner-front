package com.nhnacademy.front.util.security.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nhnacademy.front.auth.dto.response.MemberAuthResponse;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.front.util.security.dto.request.MemberAuthRequest;

/**
 * OpenFeign 사용한 로그인 어댑터
 */
@FeignClient(url = "http://${feign.client.url}/bookstore", name = "SecurityLoginAdapter")
public interface SecurityLoginAdapter {
	/**
	 * Member login api response.
	 *
	 * @param token the member auth request
	 * @return the api response
	 */
	@PostMapping("/token/login/{token}")
	ApiResponse<MemberAuthResponse> memberLogin(@PathVariable String token);

	@PostMapping("/login")
	ApiResponse<MemberAuthResponse> memberLoginId(@RequestBody MemberAuthRequest memberAuthRequest);
}