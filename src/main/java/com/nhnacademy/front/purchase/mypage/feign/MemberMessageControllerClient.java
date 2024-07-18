package com.nhnacademy.front.purchase.mypage.feign;

import com.nhnacademy.front.purchase.mypage.dto.request.UpdateMemberMessageRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadMemberMessageResponse;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadPurchaseCouponDetailResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "MemberMessageControllerClient", url = "http://${feign.client.url}")
public interface MemberMessageControllerClient {
    @GetMapping("/bookstore/messages")
    ApiResponse<Page<ReadMemberMessageResponse>> readAllById(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @GetMapping("/bookstore/messages/counts")
    ApiResponse<Long> readUnreadedMessage();

    @PutMapping("/bookstore/messages")
    ApiResponse<Void> updateMessage(@RequestBody UpdateMemberMessageRequest updateMemberMessageRequest);
}
