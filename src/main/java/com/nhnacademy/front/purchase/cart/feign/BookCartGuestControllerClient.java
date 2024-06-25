package com.nhnacademy.front.purchase.cart.feign;

import com.nhnacademy.front.purchase.cart.dto.request.CreateBookCartGuestRequest;
import com.nhnacademy.front.purchase.cart.dto.request.UpdateBookCartGuestRequest;
import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "bookCartGuestControllerClient", url = "http://localhost:8080")
public interface BookCartGuestControllerClient {
    @GetMapping("/bookstore/carts")
    ApiResponse<List<ReadBookCartGuestResponse>> readCart();

    @PostMapping("/bookstore/carts")
    ApiResponse<Void> createCart(@Valid @RequestBody CreateBookCartGuestRequest createBookCartGuestRequest);

    @PutMapping("/bookstore/carts")
    ApiResponse<Void> updateCart(@Valid @RequestBody UpdateBookCartGuestRequest updateBookCartGuestRequest);

}
