package com.nhnacademy.bookstore.purchase.bookCart.controller;

import com.nhnacademy.bookstore.purchase.bookCart.dto.request.CreateBookCartGuestRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.UpdateBookCartGuestRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.bookstore.purchase.bookCart.exception.BookCartArgumentErrorException;
import com.nhnacademy.bookstore.purchase.bookCart.service.BookCartGuestService;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * BookCart 비회원 컨트롤러.
 *
 * @author 김병우
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore")
public class BookCartGuestController {
    private final BookCartGuestService bookCartGuestService;

    /**
     * 카트 목록 반환 api.
     *
     * @author 김병우
     * @param request 쿠키 요청
     * @return 카트 목록
     */
    @GetMapping("/carts")
    public ApiResponse<List<ReadBookCartGuestResponse>> readCart(HttpServletRequest request) {
        Long cartId = getCartIdFromCookie(request);

        if (Objects.isNull(cartId)) {
            return ApiResponse.success(List.of());
        }

        return ApiResponse.success(bookCartGuestService.readAllBookCart(cartId));
    }

    /**
     * 카트 추가 api
     *
     * @param createBookCartGuestRequest 카트 추가 폼
     * @param bindingResult 검증
     * @param request 쿠키 요청
     * @param response 쿠키 응답
     * @return api 응답
     */
    @PostMapping("/carts")
    public ApiResponse<Void> createCart(
            @Valid @RequestBody CreateBookCartGuestRequest createBookCartGuestRequest,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response
        ) {

        if (bindingResult.hasErrors()) {
            throw new BookCartArgumentErrorException("폼 에러");
        }
        Long cartId = getCartIdFromCookie(request);

        cartId = bookCartGuestService.createBookCart(createBookCartGuestRequest.bookId(),
                cartId,
                createBookCartGuestRequest.quantity());

        Cookie cartCookie = new Cookie("cartId", cartId.toString());
        cartCookie.setPath("/");
        cartCookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cartCookie);

        return ApiResponse.createSuccess(null);
    }

    /**
     * 카트 목록 수정 api.
     *
     * @param updateBookCartGuestRequest 수정 요청 폼
     * @param bindingResult 검증
     * @param request 쿠키 요청
     * @return api 응답
     */
    @PutMapping("/carts")
    public ApiResponse<Void> updateCart(
            @Valid @RequestBody UpdateBookCartGuestRequest updateBookCartGuestRequest,
            BindingResult bindingResult,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BookCartArgumentErrorException("폼 에러");
        }
        Long cartId = getCartIdFromCookie(request);

        bookCartGuestService.updateBookCart(
            updateBookCartGuestRequest.bookId(),
            cartId,
            updateBookCartGuestRequest.quantity()
        );

        return ApiResponse.success(null);
    }

    /**
     * 쿠키 cartId 추출 메서드.
     *
     * @param request 쿠키 요청
     * @return cartId, null 반환
     */
    private Long getCartIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie c : cookies) {
                if (c.getName().equals("cartId")) {
                    return Long.parseLong(c.getValue());
                }
            }
        }
        return null;
    }
}
