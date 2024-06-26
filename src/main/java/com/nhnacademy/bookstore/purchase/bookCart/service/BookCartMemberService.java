package com.nhnacademy.bookstore.purchase.bookCart.service;

import java.util.List;

import com.nhnacademy.bookstore.purchase.bookCart.dto.request.CreateBookCartMemberRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.DeleteBookCartMemberRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.ReadAllBookCartMemberRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.request.UpdateBookCartMemberRequest;
import com.nhnacademy.bookstore.purchase.bookCart.dto.response.ReadAllBookCartMemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCartMemberService {
	List<ReadAllBookCartMemberResponse> readAllCartMember(ReadAllBookCartMemberRequest readAllCartMemberRequest);
	Long createBookCartMember(CreateBookCartMemberRequest createBookCartRequest);
	Long updateBookCartMember(UpdateBookCartMemberRequest updateBookCartRequest);
	Long deleteBookCartMember(DeleteBookCartMemberRequest deleteBookCartMemberRequest);

}
