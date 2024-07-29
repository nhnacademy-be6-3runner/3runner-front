package com.nhnacademy.front.purchase.purchase.controller;

import com.nhnacademy.front.purchase.cart.dto.request.CreateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import com.nhnacademy.front.purchase.cart.service.CartGuestService;
import com.nhnacademy.front.purchase.cart.service.CartMemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 주문 UI 컨트롤러.
 *
 * @author 김병우
 */
@Controller
@RequiredArgsConstructor
public class PurchaseController {
    private final BookCartControllerClient bookCartControllerClient;
    private final CartMemberService cartMemberService;
    private final CartGuestService cartGuestService;

    /**
     * 비회원, 회원 판별 주문.
     *
     * @param cartId 카트아이디
     * @param Access 맴버아이디
     * @param response HTTP RESPONSE
     * @throws IOException
     */
    @PostMapping("/purchases")
    public void purchase(
            @RequestParam(value = "cartId", required = false) Long cartId,
            @RequestParam(value = "bookId", required = false) Long bookId,
            @CookieValue(required = false) String Access,
            HttpServletResponse response
    ) throws IOException {
        if(Objects.isNull(Access)){
            if(Objects.nonNull(bookId)) {
                if (Objects.isNull(cartId)) {
                    cartId = bookCartControllerClient.createCart(CreateBookCartRequest.builder().bookId(bookId).quantity(1).build()).getBody().getData();

                    response.addCookie(cartGuestService.createNewCart(cartId));
                }
                if (cartGuestService.checkBookCart(cartId, bookId)) {
                    bookCartControllerClient.createCart(CreateBookCartRequest.builder().bookId(bookId).quantity(1).build()).getBody().getData();
                } else {
                    bookCartControllerClient.updateCart(UpdateBookCartRequest.builder().bookId(bookId).cartId(cartId).quantity(1).build());
                }


            }
            response.sendRedirect("/purchases/guests/"+cartId);

        } else {

            if(Objects.nonNull(bookId)) {

                if (cartMemberService.checkBookCart(bookId)) {
                    bookCartControllerClient.updateCart(UpdateBookCartRequest.builder().bookId(bookId).cartId(0).quantity(1).build());
                } else {
                    bookCartControllerClient.createCart(CreateBookCartRequest.builder().bookId(bookId).quantity(1).build()).getBody().getData();
                }
            }
            response.sendRedirect("/purchases/members");
        }
    }
}
