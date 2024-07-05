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
    private final BookCartControllerClient bookCartControllerClient;
    private final CartMemberService cartMemberService;
    private final CartGuestService cartGuestService;

    /**
     * 비회원, 회원 판별 주문.
     *
     * @param cartId 카트아이디
     * @param memberId 맴버아이디
     * @param response HTTP RESPONSE
     * @throws IOException
     */
    @PostMapping("/purchases")
    public void purchase(
            @RequestParam(value = "cartId", required = false) Long cartId,
            @RequestParam(value = "bookId", required = false) Long bookId,
            @RequestHeader(value = "Member-Id", required = false) Long memberId,
            HttpServletResponse response
    ) throws IOException {
        if(Objects.isNull(memberId)){
            if(Objects.nonNull(bookId)) {
                if (Objects.isNull(cartId)) {
                    cartId = bookCartControllerClient.createCart(CreateBookCartRequest.builder().bookId(bookId).quantity(1).build(), memberId).getBody().getData();

                    response.addCookie(cartGuestService.createNewCart(cartId));
                }
                if (cartGuestService.checkBookCart(cartId, bookId)) {
                    bookCartControllerClient.createCart(CreateBookCartRequest.builder().bookId(bookId).quantity(1).build(), memberId).getBody().getData();
                }


                bookCartControllerClient.updateCart(UpdateBookCartRequest.builder().bookId(bookId).cartId(cartId).quantity(1).build(), memberId);
            }
            response.sendRedirect("purchases/guests/"+cartId);

        } else {

            if(Objects.nonNull(bookId)) {

                if (cartMemberService.checkBookCart(memberId, bookId)) {
                    bookCartControllerClient.updateCart(UpdateBookCartRequest.builder().bookId(bookId).cartId(memberId).quantity(1).build(), memberId);

                }
                bookCartControllerClient.createCart(CreateBookCartRequest.builder().bookId(bookId).quantity(1).build(), memberId).getBody().getData();
            }
            response.sendRedirect("purchases/members");

        }
    }
}
