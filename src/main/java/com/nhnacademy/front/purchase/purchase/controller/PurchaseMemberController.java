package com.nhnacademy.front.purchase.purchase.controller;

import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.feign.PurchaseMemberControllerClient;
import com.nhnacademy.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PurchaseMemberController {
    private final PurchaseMemberControllerClient purchaseMemberControllerClient;


    @GetMapping("/order")
    public String order(){
        return "order";
    }

    @GetMapping("/order/complete")
    public String orderComplete(){
        return "order-complete";
    }


}
