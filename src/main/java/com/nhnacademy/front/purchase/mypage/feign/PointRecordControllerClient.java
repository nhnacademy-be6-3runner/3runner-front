package com.nhnacademy.front.purchase.mypage.feign;

import com.nhnacademy.front.purchase.mypage.dto.request.ReadPointRecordRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadPointRecordResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PointRecordControllerClient", url = "${feign.client.url}")
public interface PointRecordControllerClient {
	@PostMapping("/bookstore/members/points")
	ApiResponse<Page<ReadPointRecordResponse>> readPointRecord(
		@RequestBody ReadPointRecordRequest readPointRecordRequest
	);
}