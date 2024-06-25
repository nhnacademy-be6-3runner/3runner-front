package com.nhnacademy.front.purchase.cart.controller;

import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.front.purchase.cart.feign.BookCartGuestControllerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final BookCartGuestControllerClient bookCartGuestControllerClient;

    @GetMapping("/api")
    public String testMain(){
        return "test-main";
    }
    @PostMapping("/api/carts")
    public String cart(
            @RequestParam(value = "quantity") Integer quantity,
            @RequestParam(value = "bookId") Long bookId,
            Model model) {
//        bookCartGuestControllerClient.createCart(
//                CreateBookCartGuestRequest.builder()
//                        .bookId(bookId)
//                        .quantity(quantity)
//                        .build()
//        );
//
//        ApiResponse<List<ReadBookCartGuestResponse>> apiResponse =  bookCartGuestControllerClient.readCart();
//        List<ReadBookCartGuestResponse> response = apiResponse.getBody().getData();

        List<ReadBookCartGuestResponse> response = List.of(
                ReadBookCartGuestResponse.builder().bookCartId(1L).url("").price(1000).title("test1").quantity(3).build(),
                ReadBookCartGuestResponse.builder().bookCartId(2L).url("").price(2000).title("test2").quantity(4).build()
        );

        model.addAttribute("response", response);

        return "cart";
    }
}
