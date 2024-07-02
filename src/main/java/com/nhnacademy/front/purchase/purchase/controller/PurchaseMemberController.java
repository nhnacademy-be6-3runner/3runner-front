package com.nhnacademy.front.purchase.purchase.controller;

import com.nhnacademy.front.purchase.purchase.feign.PurchaseMemberControllerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequiredArgsConstructor
public class PurchaseMemberController {
    @GetMapping("/api/purchases/members")
    public String purchase(@RequestHeader Long memberId){
        return "/purchase/member/purchase";
    }
}
