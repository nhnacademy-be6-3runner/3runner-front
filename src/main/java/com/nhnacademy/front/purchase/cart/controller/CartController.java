package com.nhnacademy.front.purchase.cart.controller;

import com.nhnacademy.front.purchase.cart.dto.request.CreateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.request.ReadAllBookCartMemberRequest;
import com.nhnacademy.front.purchase.cart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import com.nhnacademy.front.purchase.cart.service.CartGuestService;
import com.nhnacademy.front.purchase.cart.service.CartMemberService;
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
    private final CartMemberService cartMemberService;
    private final CartGuestService cartGuestService;


    /**
     * 장바구니 UI
     *
     * @param cartId 카트 아이디
     * @param access 맴버 아이디
     * @param model 모델
     * @return cart view
     */
    @GetMapping("/carts")
    public String cartView(
            @CookieValue(value = "cartId", required = false) Long cartId,
            @CookieValue(value = "Access", required = false) String access,
            HttpServletResponse response,
            Model model){

        //model.addAttribute("memberId", memberId);
        if (!Objects.nonNull(access)) {        //회원 카트
            if(Objects.isNull(cartId)){
                cartId = bookCartControllerClient.createGuestCart().getBody().getData();
                response.addCookie(cartGuestService.createNewCart(cartId));
            }

            List<ReadBookCartGuestResponse> items = bookCartControllerClient
                    .readCart(cartId)
                    .getBody().getData();

            model.addAttribute("response", items);
            model.addAttribute("cartId", cartId.toString());
        } else {

            List<ReadAllBookCartMemberResponse> items = bookCartControllerClient
                    .readAllBookCartMember()
                    .getBody().getData();

            model.addAttribute("response", items);
            model.addAttribute("Access", access);
            model.addAttribute("cartId", 0);
        }

        return "purchase/cart";
    }

    /**
     * 장바구니 넣기.
     *
     * @param cartId 카트아이디
     * @param access 맴버아이디
     * @param quantity 수량
     * @param bookId 북아이디
     * @param response httpresponse
     * @param model 모델
     * @throws IOException
     */
    @PostMapping("/carts")
    public void cart(
            @CookieValue(value = "cartId", required = false) Long cartId,
            @CookieValue(value = "Access", required = false) String access,
            @RequestParam(value = "quantity") Integer quantity,
            @RequestParam(value = "bookId") Long bookId,
            HttpServletResponse response,
            Model model) throws IOException {

        if (Objects.nonNull(access)) {        //회원 카트
            if(cartMemberService.checkBookCart(bookId)){

                bookCartControllerClient.updateCart(UpdateBookCartRequest.builder().bookId(bookId).quantity(quantity).build());

                response.sendRedirect("/carts");
                return;
            }

            bookCartControllerClient.createCart(CreateBookCartRequest.builder().bookId(bookId).quantity(quantity).build()).getBody().getData();

            response.sendRedirect("/carts");

        } else {    //비회원 카트
            if(Objects.isNull(cartId)){ //쿠키 없을시 쿠키 발급
                cartId = bookCartControllerClient.createCart(CreateBookCartRequest.builder().bookId(bookId).quantity(quantity).build()).getBody().getData();

                response.addCookie(cartGuestService.createNewCart(cartId));
                response.sendRedirect("/carts");
                return;
            }

            if (cartGuestService.checkBookCart(cartId, bookId)) {
                bookCartControllerClient.updateCart(UpdateBookCartRequest.builder().bookId(bookId).cartId(cartId).quantity(quantity).build());


                response.sendRedirect("/carts");
                return;
            }

            bookCartControllerClient.createCart(CreateBookCartRequest.builder().bookId(bookId).userId(cartId).quantity(quantity).build()).getBody().getData();

            response.sendRedirect("/carts");
        }
    }

    /**
     * 장바구니 테스트 담기.
     *
     * @param cartId 카트 아이디
     * @param model 모델
     * @return test-main 뷰
     */
    @GetMapping("/test")
    public String testMain(
            @CookieValue(value = "cartId", required = false) Long cartId,
            Model model){
        model.addAttribute("cartId", cartId);
        return "test-main";
    }
}
