package com.nhnacademy.bookstore.purchase.bookCart.controller;

import com.nhnacademy.bookstore.purchase.bookCart.dto.request.CreateBookCartGuestRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.RemoveBookCartGuestRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.bookstore.purchase.bookCart.exception.BookCartArgumentErrorException;
import com.nhnacademy.bookstore.purchase.bookCart.service.BookCartGuestService;
import com.nhnacademy.bookstore.util.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookstore")
public class BookCartGuestController {
    private final BookCartGuestService bookCartGuestService;

    @GetMapping("/cart")
    public ApiResponse<List<ReadBookCartGuestResponse>> getCart(HttpServletRequest request){
        Long cartId = null;
        Cookie[] cookies = request.getCookies();

        for(Cookie c : cookies){
            if(c.getName().equals("cartId")){
                cartId = Long.parseLong(c.getValue());
            }
        }

        return ApiResponse.createSuccess(bookCartGuestService.readAllBookCart(cartId));
    }

    @PostMapping("/cart")
    public ApiResponse<Void> addCart(
            @Valid @RequestBody CreateBookCartGuestRequest createBookCartGuestRequest,
            BindingResult bindingResult,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new BookCartArgumentErrorException("폼 에러");
        }
        Long cartId = null;
        Cookie[] cookies = request.getCookies();

        for(Cookie c : cookies){
            if(c.getName().equals("cartId")){
                cartId = Long.parseLong(c.getValue());
            }
        }


        bookCartGuestService.createBookCart(createBookCartGuestRequest.bookId(),
                cartId,
                createBookCartGuestRequest.quantity());

        return ApiResponse.createSuccess(null);
    }

    @DeleteMapping("/cart")
    public ApiResponse<Void> deleteCart(
            @Valid @RequestBody RemoveBookCartGuestRequest removeBookCartGuestRequest,
            BindingResult bindingResult,
            HttpServletRequest request){

        if (bindingResult.hasErrors()) {
            throw new BookCartArgumentErrorException("폼 에러");
        }
        Long cartId = null;
        Cookie[] cookies = request.getCookies();

        for(Cookie c : cookies){
            if(c.getName().equals("cartId")){
                cartId = Long.parseLong(c.getValue());
            }
        }
        bookCartGuestService.removeBookCart(removeBookCartGuestRequest.bookId(), cartId, removeBookCartGuestRequest.quantity());

        return ApiResponse.deleteSuccess(null);
    }
}
