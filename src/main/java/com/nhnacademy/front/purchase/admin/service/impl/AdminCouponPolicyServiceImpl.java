package com.nhnacademy.front.purchase.admin.service.impl;

import com.nhnacademy.front.book.book.controller.feign.BookClient;
import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.purchase.admin.feign.CategoryControllerClient;
import com.nhnacademy.front.purchase.admin.feign.CouponPolicyControllerClient;
import com.nhnacademy.front.purchase.admin.service.AdminCouponPolicyService;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateBookCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCategoryCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateFixedCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateRatioCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponTypeResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponUsageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminCouponPolicyServiceImpl implements AdminCouponPolicyService {
	private final CouponPolicyControllerClient couponPolicyControllerClient;
	private final CategoryControllerClient categoryControllerClient;
	private final BookClient bookControllerClient;

	@Override
	public List<CategoryParentWithChildrenResponse> getCategories() {
		return categoryControllerClient.readAllCategories().getBody().getData();
	}

	@Override
	public Page<BookListResponse> getBooks(int size, int page, String sort) {
		return bookControllerClient.readAllBooks(page, size, sort).getBody().getData();
	}

	@Override
	public List<ReadCouponTypeResponse> getTypes() {
		return couponPolicyControllerClient.readAllTypes().getBody().getData();
	}

	@Override
	public List<ReadCouponUsageResponse> getUsages() {
		return couponPolicyControllerClient.readAllUsages().getBody().getData();
	}

	@Override
	public Long createCategoryUsages(CreateCategoryCouponRequest createCategoryCouponRequest) {
		return couponPolicyControllerClient.createCategoryCouponPolicy(createCategoryCouponRequest).getBody().getData();
	}

	@Override
	public Long createBookUsages(CreateBookCouponRequest createBookCouponRequest) {
		return couponPolicyControllerClient.createBookCouponPolicy(createBookCouponRequest).getBody().getData();
	}

	@Override
	public Long createFixedTypes(CreateFixedCouponRequest createFixedCouponRequest) {
		return couponPolicyControllerClient.createFixedCouponPolicy(createFixedCouponRequest).getBody().getData();
	}

	@Override
	public Long createRatioTypes(CreateRatioCouponRequest createRatioCouponRequest) {
		return couponPolicyControllerClient.createRatioCouponPolicy(createRatioCouponRequest).getBody().getData();
	}
}