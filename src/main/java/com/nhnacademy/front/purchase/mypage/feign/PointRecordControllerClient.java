package com.nhnacademy.front.purchase.mypage.feign;

import com.nhnacademy.front.purchase.mypage.dto.request.ReadPointRecordRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadPointRecordResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Objects;

@FeignClient(name = "PointRecordControllerClient", url = "http://${feign.client.url}")
public interface PointRecordControllerClient {
    @PostMapping("/bookstore/members/points")
    ApiResponse<Page<ReadPointRecordResponse>> readPointRecord(
            @RequestBody ReadPointRecordRequest readPointRecordRequest
    );
}