package com.nhnacademy.front.purchase.mypage.controller;

import com.nhnacademy.front.purchase.mypage.dto.request.ReadPointRecordRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadPointRecordResponse;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadPurchaseCouponDetailResponse;
import com.nhnacademy.front.purchase.mypage.service.PointDetailService;
import com.nhnacademy.front.purchase.mypage.service.PurchaseCouponDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PointMyPageController {
    private final PointDetailService pointDetailService;
    @GetMapping("/mypages/points")
    public String getPoints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model){
        Page<ReadPointRecordResponse> responses = pointDetailService
                .readPurchaseCouponForClient(
                        ReadPointRecordRequest.builder()
                        .page(page)
                        .size(size)
                        .sort(null).build()
                );

        model.addAttribute("responses",responses);

        return "purchase/mypage/point-detail";
    }
}
