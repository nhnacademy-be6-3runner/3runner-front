package com.nhnacademy.front.purchase.mypage.controller;

import com.nhnacademy.front.purchase.mypage.dto.request.UpdateMemberMessageRequest;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadMemberMessageResponse;
import com.nhnacademy.front.purchase.mypage.dto.response.ReadPurchaseCouponDetailResponse;
import com.nhnacademy.front.purchase.mypage.service.MemberMessageService;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponFormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberMessageController {
    private final MemberMessageService memberMessageService;
    @GetMapping("/mypages/messages")
    public String getCoupons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model){

        Page<ReadMemberMessageResponse> responses = memberMessageService.readAllById(page, size);
        model.addAttribute("messagePage", responses);

        return "purchase/mypage/message";
    }


    @PostMapping("/mypages/messages/read")
    public String readMessage(@ModelAttribute UpdateMemberMessageRequest updateMemberMessageRequest) {
        memberMessageService.updateMessage(updateMemberMessageRequest);
        return "redirect:/mypages/messages";
    }
}
