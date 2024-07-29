package com.nhnacademy.front.purchase.admin.feign;

import com.nhnacademy.front.purchase.admin.dto.PointPolicyResponseRequest;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.global.config.FeignConfiguration;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PointPolicyControllerClient", url = "${feign.client.url}", configuration = FeignConfiguration.class)
public interface PointPolicyControllerClient {

	@PostMapping("/bookstore/points/policies")
	ApiResponse<Long> saveOrUpdatePolicy(@RequestBody PointPolicyResponseRequest pointPolicyResponseRequest);

	@GetMapping("/bookstore/points/policies")
	ApiResponse<List<PointPolicyResponseRequest>> readPolicy();

	@GetMapping("/bookstore/points/policies/{policyKey}")
	ApiResponse<PointPolicyResponseRequest> readOne(@PathVariable String policyKey);
}
