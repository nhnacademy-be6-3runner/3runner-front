package com.nhnacademy.bookstore.purchase.bookCart.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhnacademy.bookstore.purchase.bookCart.dto.request.CreateBookCartMemberRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.DeleteBookCartMemberRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.UpdateBookCartMemberRequest;
import com.nhnacademy.bookstore.purchase.bookCart.exception.CreateBookCartMemberRequestFormException;
import com.nhnacademy.bookstore.purchase.bookCart.exception.DeleteBookCartMemberRequestFormException;
import com.nhnacademy.bookstore.purchase.bookCart.exception.UpdateBookCartMemberRequestFormException;
import com.nhnacademy.bookstore.purchase.bookCart.service.BookCartMemberService;
import com.nhnacademy.bookstore.util.ApiResponse;
import com.nhnacademy.bookstore.util.ValidationUtils;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 회원 book-cart controller
 *
 * @author 정주혁
 */
@RestController
@RequestMapping("/bookstore/members/carts")
@RequiredArgsConstructor
public class BookCartMemberController {
	private final BookCartMemberService bookCartMemberService;

	@PostMapping
	public ApiResponse<Long> createBookCartMember(@RequestBody @Valid CreateBookCartMemberRequest request,@RequestHeader(name = "Member-Id") Long userId, BindingResult bindingResult) {
		ValidationUtils.validateBindingResult(bindingResult, new CreateBookCartMemberRequestFormException());
		request = CreateBookCartMemberRequest.builder()
			.bookId(request.bookId())
			.quantity(request.quantity())
			.userId(userId)
			.build();
		return ApiResponse.createSuccess(bookCartMemberService.createBookCartMember(request));
	}



	@PutMapping
	public ApiResponse<Long> updateBookCartMember(@RequestBody @Valid UpdateBookCartMemberRequest request, BindingResult bindingResult) {
		ValidationUtils.validateBindingResult(bindingResult,new UpdateBookCartMemberRequestFormException());
		return ApiResponse.success(bookCartMemberService.updateBookCartMember(request));
	}

	@DeleteMapping
	public ApiResponse<Long> deleteBookCartMember(@RequestBody @Valid DeleteBookCartMemberRequest request, BindingResult bindingResult) {
		ValidationUtils.validateBindingResult(bindingResult, new DeleteBookCartMemberRequestFormException());

		return ApiResponse.deleteSuccess(bookCartMemberService.deleteBookCartMember(request));
	}
}
