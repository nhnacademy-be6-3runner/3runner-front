package com.nhnacademy.bookstore.purchase.cart.controller;

import com.nhnacademy.bookstore.purchase.cart.service.CartMemberService;
import com.nhnacademy.bookstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartMemberService cartMemberService;
    @PostMapping("/bookstore/guests/carts")
    public ApiResponse<Long> createCarts() {
        return ApiResponse.createSuccess(cartMemberService.createGuestCart());
    }
}
