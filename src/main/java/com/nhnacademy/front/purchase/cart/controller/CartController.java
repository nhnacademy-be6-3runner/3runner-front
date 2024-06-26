package com.nhnacademy.front.purchase.cart.controller;

import com.nhnacademy.front.purchase.cart.dto.request.CreateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {
    private final BookCartControllerClient bookCartGuestControllerClient;

    @GetMapping()
    public String testMain(
            @CookieValue(value = "cartId", required = false) Long cartId,
            Model model){
        model.addAttribute("cartId", cartId);
        return "test-main";
    }
    @GetMapping("/carts")
    public String cartView(
            @CookieValue(value = "cartId", required = false) Long cartId,
            @RequestHeader(value = "Member-Id", required = false) Long memberId,
            Model model){

        log.info("{}",cartId);
        List<ReadBookCartGuestResponse> list = bookCartGuestControllerClient.readCart(cartId).getBody().getData();

        model.addAttribute("response", list);
        model.addAttribute("cartId", cartId.toString());

        return "cart";
    }
    @PostMapping("/carts")
    public String cart(
            @CookieValue(value = "cartId", required = false) Long cartId,
            @RequestHeader(value = "Member-Id", required = false) Long memberId,
            @RequestParam(value = "quantity") Integer quantity,
            @RequestParam(value = "bookId") Long bookId,
            HttpServletResponse response,
            Model model) throws IOException {

        if(Objects.isNull(cartId)){
            cartId = bookCartGuestControllerClient.createCart(
                    CreateBookCartRequest.builder()
                            .bookId(bookId)
                            .quantity(quantity)
                            .build(),
                    memberId).getBody().getData();

            log.info("created : {}",cartId);

            Cookie cartCookie = new Cookie("cartId", cartId.toString());
            cartCookie.setHttpOnly(true);
            cartCookie.setSecure(true);
            cartCookie.setPath("/");
            cartCookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cartCookie);
            response.sendRedirect("/api/carts");
        } else {
            bookCartGuestControllerClient.updateCart(
                    UpdateBookCartRequest.builder().
                            bookId(bookId).
                            cartId(cartId).
                            quantity(quantity)
                            .build(),
                    memberId
            );

            response.sendRedirect("/api/carts");
        }

        return "cart";
    }
}
