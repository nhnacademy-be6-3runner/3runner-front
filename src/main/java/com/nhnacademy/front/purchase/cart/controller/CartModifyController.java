package com.nhnacademy.front.purchase.cart.controller;

import com.nhnacademy.front.purchase.cart.dto.request.DeleteBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CartModifyController {
    private final BookCartControllerClient bookCartControllerClient;

    @GetMapping("/carts/{bookId}/updates/{quantity}")
    public String cartUpdate(
            @PathVariable Long bookId,
            @PathVariable Integer quantity,
            @CookieValue(required = false) Long cartId,
            @CookieValue(required = false) String Access
    ) {

        if(Objects.isNull(Access)){
            bookCartControllerClient.updateCart(UpdateBookCartRequest.builder().bookId(bookId).cartId(cartId).quantity(quantity).build());
        } else {
            bookCartControllerClient.updateCart(UpdateBookCartRequest.builder().bookId(bookId).cartId(0).quantity(quantity).build());
        }
        return "cart-update";
    }


    @GetMapping("/carts/deletes/books/{bookCartId}")
    public String cartDelete(
            @PathVariable Long bookCartId,
            @CookieValue(required = false) Long cartId,
            @CookieValue(required = false) String Access
    ) {
        if(Objects.isNull(Access)){
            bookCartControllerClient.deleteCart(DeleteBookCartRequest.builder().bookCartId(bookCartId).cartId(cartId).build());
        } else {
            bookCartControllerClient.deleteCart(DeleteBookCartRequest.builder().bookCartId(bookCartId).cartId(0).build());
        }

        return "cart-delete";
    }


    @GetMapping("/carts/deletes")
    public String cartDeleteAll(
            @CookieValue(required = false) Long cartId,
            @CookieValue(required = false) String Access
    ) {

        if(Objects.isNull(Access)){
            bookCartControllerClient.deleteAllCart(cartId);
        } else {
            cartId = 0L;
            bookCartControllerClient.deleteAllCart(cartId);
        }
        return "cart-delete-all";
    }
}
