package com.nhnacademy.front.purchase.cart.controller;

import com.nhnacademy.front.purchase.cart.dto.request.CreateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.request.ReadAllBookCartMemberRequest;
import com.nhnacademy.front.purchase.cart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 장바구니 UI 컨트롤러.
 *
 * @author 김병우
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {
    private final BookCartControllerClient bookCartControllerClient;

    /**
     * 장바구니 테스트 담기.
     *
     * @param cartId 카트 아이디
     * @param model 모델
     * @return test-main 뷰
     */
    @GetMapping("/test-add-cart")
    public String testMain(
            @CookieValue(value = "cartId", required = false) Long cartId,
            Model model){
        model.addAttribute("cartId", cartId);
        return "test-main";
    }

    /**
     * 장바구니 UI
     *
     * @param cartId 카트 아이디
     * @param memberId 맴버 아이디
     * @param model 모델
     * @return cart view
     */
    @GetMapping("/carts")
    public String cartView(
            @CookieValue(value = "cartId", required = false) Long cartId,
            @RequestHeader(value = "Member-Id", required = false) Long memberId,
            Model model){

        model.addAttribute("memberId", memberId);
        if (Objects.isNull(memberId)){

            List<ReadBookCartGuestResponse> items = bookCartControllerClient
                    .readCart(cartId)
                    .getBody().getData();

            model.addAttribute("response", items);
            model.addAttribute("cartId", cartId.toString());
        } else {

            List<ReadAllBookCartMemberResponse> items = bookCartControllerClient
                    .readAllBookCartMember(memberId)
                    .getBody().getData();

            model.addAttribute("response", items);
            model.addAttribute("cartId", memberId.toString());
        }

        return "purchase/cart";
    }

    /**
     * 장바구니 넣기.
     *
     * @param cartId 카트아이디
     * @param memberId 맴버아이디
     * @param quantity 수량
     * @param bookId 북아이디
     * @param response httpresponse
     * @param model 모델
     * @throws IOException
     */
    @PostMapping("/carts")
    public void cart(
            @CookieValue(value = "cartId", required = false) Long cartId,
            @RequestHeader(value = "Member-Id", required = false) Long memberId,
            @RequestParam(value = "quantity") Integer quantity,
            @RequestParam(value = "bookId") Long bookId,
            HttpServletResponse response,
            Model model) throws IOException {

        if (Objects.nonNull(memberId)) {
            List<ReadAllBookCartMemberResponse> list = bookCartControllerClient
                    .readAllBookCartMember(memberId)
                    .getBody().getData();

            for(ReadAllBookCartMemberResponse l : list){
                if(l.bookId().equals(bookId)){
                    bookCartControllerClient.updateCart(
                            UpdateBookCartRequest.builder()
                                    .bookId(bookId)
                                    .cartId(memberId)
                                    .quantity(quantity)
                                    .build(),
                            memberId
                    );

                    response.sendRedirect("/carts");
                    return;
                }
            }

            bookCartControllerClient.createCart(
                    CreateBookCartRequest.builder()
                            .bookId(bookId)
                            .quantity(quantity)
                            .build(),
                    memberId).getBody().getData();

            response.sendRedirect("/carts");
        } else {
            if(Objects.isNull(cartId)){
                cartId = bookCartControllerClient.createCart(
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
                bookCartControllerClient.updateCart(
                        UpdateBookCartRequest.builder()
                                .bookId(bookId)
                                .cartId(cartId)
                                .quantity(quantity)
                                .build(),
                        memberId
                );

                response.sendRedirect("/api/carts");
            }
        }
    }
}
