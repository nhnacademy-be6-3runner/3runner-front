package com.nhnacademy.front.purchase.cart.service;

import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartGuestService {
    private final BookCartControllerClient bookCartControllerClient;
    public boolean checkBookCart(Long cartId ,Long bookId) {
        List<ReadBookCartGuestResponse> list = bookCartControllerClient.readCart(cartId).getBody().getData();

        for (ReadBookCartGuestResponse l : list) {
            if (l.bookId().equals(bookId)) {
                return true;
            }
        }
        return false;
    }

    public Cookie createNewCart(Long cartId){
        Cookie cartCookie = new Cookie("cartId", cartId.toString());
        cartCookie.setHttpOnly(true);
        cartCookie.setSecure(false);
        cartCookie.setPath("/");
        cartCookie.setMaxAge(60 * 60 * 24 * 7);
        return cartCookie;
    }

}