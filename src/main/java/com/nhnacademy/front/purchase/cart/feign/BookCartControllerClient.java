package com.nhnacademy.front.purchase.cart.feign;

import com.nhnacademy.front.purchase.cart.dto.request.CreateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.request.DeleteBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.request.UpdateBookCartRequest;
import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.front.util.ApiResponse;
import com.nhnacademy.global.config.FeignConfiguration;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bookCartControllerClient", url = "${feign.client.url}", configuration = FeignConfiguration.class)
public interface BookCartControllerClient {
	@GetMapping("/bookstore/carts/{cartId}")
	ApiResponse<List<ReadBookCartGuestResponse>> readCart(@PathVariable("cartId") Long cartId);

	@GetMapping("/bookstore/carts")
	ApiResponse<List<ReadAllBookCartMemberResponse>> readAllBookCartMember();

	@PostMapping("/bookstore/carts")
	ApiResponse<Long> createCart(
		@Valid @RequestBody CreateBookCartRequest createBookCartGuestRequest
	);

	@PutMapping("/bookstore/carts")
	ApiResponse<Long> updateCart(
		@Valid @RequestBody UpdateBookCartRequest updateBookCartGuestRequest
	);

	@DeleteMapping("/bookstore/carts")
	ApiResponse<Long> deleteCart(@Valid @RequestBody DeleteBookCartRequest deleteBookCartGuestRequest);

	@PostMapping("/bookstore/guests/carts")
	ApiResponse<Long> createGuestCart();

	@DeleteMapping("/bookstore/carts/{cartId}")
	ApiResponse<Long> deleteAllCart(@PathVariable(required = false) Long cartId);
}
