package com.nhnacademy.front.purchase.purchase.controller;

import com.nhnacademy.front.purchase.cart.dto.request.CreateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Objects;

/**
 * 주문 UI 컨트롤러.
 *
 * @author 김병우
 */
@Controller
@RequiredArgsConstructor
public class PurchaseController {
    private final BookCartControllerClient bookCartGuestControllerClient;

    /**
     * 비회원, 회원 판별 주문.
     *
     * @param cartId 카트아이디
     * @param memberId 맴버아이디
     * @param response HTTP RESPONSE
     * @throws IOException
     */
    @PostMapping("/api/purchases")
    public void purchase(
            @RequestParam(value = "cartId", required = false) Long cartId,
            @RequestParam(value = "bookId", required = false) Long bookId,
            @RequestHeader(value = "Member-Id", required = false) Long memberId,
            HttpServletResponse response
    ) throws IOException {
        if(Objects.nonNull(bookId)){
            if(Objects.isNull(cartId)){
                cartId = bookCartGuestControllerClient.createCart(
                        CreateBookCartRequest.builder()
                                .bookId(bookId)
                                .quantity(1)
                                .build(),
                        memberId).getBody().getData();

                Cookie cartCookie = new Cookie("cartId", cartId.toString());
                cartCookie.setHttpOnly(true);
                cartCookie.setSecure(true);
                cartCookie.setPath("/");
                cartCookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(cartCookie);
            } else {
                bookCartGuestControllerClient.updateCart(
                        UpdateBookCartRequest.builder().
                                bookId(bookId).
                                cartId(cartId).
                                quantity(1)
                                .build(),
                        memberId
                );
            }
        }

        if (Objects.isNull(memberId)) {
            response.sendRedirect("/api/purchases/guests/"+cartId);
        } else {
            response.sendRedirect("/api/purchases/members");
        }
    }
}
