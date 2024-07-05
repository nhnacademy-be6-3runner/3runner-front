package com.nhnacademy.front.purchase.purchase.controller;

import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 비회원 주문 UI 컨트롤러.
 *
 * @author 김병우.
 */
@Controller
@RequiredArgsConstructor
public class PurchaseGuestController {
    private final BookCartControllerClient bookCartGuestControllerClient;

    /**
     * 비회원 주문 UI.
     *
     * @param cartId 카트아이디
     * @param model 모델
     * @return purchase view
     */
    @GetMapping("/purchases/guests/{cartId}")
    public String purchase(@PathVariable("cartId") Long cartId, Model model){
        List<ReadBookCartGuestResponse> items = bookCartGuestControllerClient.readCart(cartId).getBody().getData();

        model.addAttribute("response", items);
        model.addAttribute("cartId", cartId);
        model.addAttribute("orderNumber", UUID.randomUUID());

        return "purchase/guest/purchase";
    }

    /**
     * 비회원 주소 UI.
     *
     * @param roadFullAddr 주소추가
     * @param model 모델
     * @return address view
     */
    @PostMapping("/purchases/guests/addresses")
    public String address(String roadFullAddr, Model model){

        model.addAttribute("roadFullAddr", roadFullAddr);
        return "purchase/guest/address";
    }
}
