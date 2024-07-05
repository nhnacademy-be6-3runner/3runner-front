package com.nhnacademy.front.purchase.cart.service;

import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartMemberService {
    private final BookCartControllerClient bookCartControllerClient;

    public boolean checkBookCart(Long memberId ,Long bookId) {
        List<ReadAllBookCartMemberResponse> list = bookCartControllerClient.readAllBookCartMember(memberId).getBody().getData();

        for (ReadAllBookCartMemberResponse l : list) {
            if (l.bookId().equals(bookId)) {
                return true;
            }
        }
        return false;
    }

}
