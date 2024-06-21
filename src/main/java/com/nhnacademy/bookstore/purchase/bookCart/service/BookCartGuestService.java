package com.nhnacademy.bookstore.purchase.bookCart.service;

import com.nhnacademy.bookstore.entity.bookCart.BookCart;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.CreateBookCartGuestRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadBookCartGuestResponse;
import java.util.List;

public interface BookCartGuestService {

    Long createBookCart(Long bookId, Long cartId, int quantity);

    void removeBookCart(Long bookId, Long cartId, int quantity);

    List<ReadBookCartGuestResponse> readAllBookCart(Long cartId);
}
