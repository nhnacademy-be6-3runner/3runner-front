package com.nhnacademy.front.purchase.payment.controller;

import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

/**
 * 토스 페이 UI 컨트롤러.
 *
 * @author 김병우
 */
@Controller
@RequiredArgsConstructor
public class PaymentGuestController {
    private final BookCartControllerClient bookCartControllerClient;

    /**
     * 토스페이 성공 UI.
     *
     * @param cartId 카트아이디
     * @param address 전체주소
     * @param password 비밀번호
     * @param paymentType 결제종류
     * @param orderId 오더넘버
     * @param paymentKey 결제키
     * @param amount 결제금액
     * @param model 모델
     * @return success view
     */
    @GetMapping("/payments/guests/success")
    public String paymentSuccessPage(
            @CookieValue(value = "Access",required = false) String access,
            @RequestParam(required = false) Long cartId,
            @RequestParam(required = false) String isPacking,
            @RequestParam(required = false) String shipping,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String password,
            @RequestParam String paymentType,
            @RequestParam String orderId,
            @RequestParam String paymentKey,
            @RequestParam String amount,
            Model model
    ) {
        List<ReadBookCartGuestResponse> items = bookCartControllerClient.readCart(cartId).getBody().getData();

        model.addAttribute("response", items);
        model.addAttribute("cartId", cartId);
        model.addAttribute("address", address);
        model.addAttribute("password", password);
        model.addAttribute("paymentType", paymentType);
        model.addAttribute("orderId", orderId);
        model.addAttribute("paymentKey", paymentKey);
        model.addAttribute("amount", amount);
        model.addAttribute("isPacking", isPacking);
        model.addAttribute("shipping", shipping);

        return "purchase/guest/success";
    }


    /**
     * 토스페이 실패 UI.
     *
     * @param message 오류메시지
     * @param code 오류코드
     * @param model 모델
     * @return fail view
     */
    @GetMapping("/payments/guests/fail")
    public String paymentFailPage(
            @RequestParam String message,
            @RequestParam String code,
            Model model
    ) {
        model.addAttribute("message", message);
        model.addAttribute("code", code);
        return "purchase/guest/fail";
    }
}
