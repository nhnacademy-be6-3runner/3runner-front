package com.nhnacademy.front.purchase.cart.service;

import com.nhnacademy.front.purchase.cart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartMemberService {
    boolean checkBookCart(List<ReadAllBookCartMemberResponse> list ,Long bookId) {
        for (ReadAllBookCartMemberResponse l : list) {
            if (l.bookId().equals(bookId)) {
                return true;
            }
        }
        return false;
    }

}
