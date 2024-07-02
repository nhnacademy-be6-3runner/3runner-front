package com.nhnacademy.front.purchase.cart.feign;

import com.nhnacademy.front.purchase.cart.dto.request.CreateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.request.DeleteBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.global.config.FeignConfiguration;
import com.nhnacademy.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "bookCartControllerClient", url = "http://${feign.client.url}" , configuration = FeignConfiguration.class)
public interface BookCartControllerClient {
        @GetMapping("/bookstore/carts/{cartId}")
        ApiResponse<List<ReadBookCartGuestResponse>> readCart(@PathVariable("cartId") Long cartId);

        @GetMapping("/bookstore/carts")
        ApiResponse<List<ReadAllBookCartMemberResponse>> readAllBookCartMember(@RequestHeader(name = "Member-Id") Long userId);

        @PostMapping("/bookstore/carts")
        ApiResponse<Long> createCart(
                @Valid @RequestBody CreateBookCartRequest createBookCartGuestRequest,
                @RequestHeader(value = "Member-Id", required = false) Long memberId
        );

        @PutMapping("/bookstore/carts")
        ApiResponse<Long> updateCart(
                @Valid @RequestBody UpdateBookCartRequest updateBookCartGuestRequest,
                @RequestHeader(value = "Member-Id", required = false) Long memberId
        );

        @DeleteMapping()
        ApiResponse<Long> deleteCart(
                @Valid @RequestBody DeleteBookCartRequest deleteBookCartGuestRequest,
                @RequestHeader(value = "Member-Id", required = false) Long memberId
        );
}
