package com.nhnacademy.bookstore.purchase.bookCart.service;

import java.util.List;

import com.nhnacademy.bookstore.purchase.bookCart.dto.request.*;
import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadAllBookCartMemberResponse;

public interface BookCartMemberService {
	List<ReadAllBookCartMemberResponse> readAllCartMember(ReadAllBookCartMemberRequest readAllCartMemberRequest);

	Long createBookCartMember(CreateBookCartRequest createBookCartRequest);

	Long updateBookCartMember(UpdateBookCartRequest updateBookCartRequest, Long memberId);

	Long deleteBookCartMember(DeleteBookCartRequest deleteBookCartMemberRequest, Long memberId);

	Long deleteAllBookCart(Long memberId);
}
