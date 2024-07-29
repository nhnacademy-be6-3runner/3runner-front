package com.nhnacademy.front.purchase.admin.service;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateBookCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCategoryCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateFixedCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateRatioCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponTypeResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponUsageResponse;
import java.util.List;
import org.springframework.data.domain.Page;

public interface AdminCouponPolicyService {
	List<CategoryParentWithChildrenResponse> getCategories();

	Page<BookListResponse> getBooks(int size, int page, String sort);

	List<ReadCouponUsageResponse> getUsages();

	List<ReadCouponTypeResponse> getTypes();

	Long createCategoryUsages(CreateCategoryCouponRequest createCategoryCouponRequest);

	Long createBookUsages(CreateBookCouponRequest createBookCouponRequest);

	Long createFixedTypes(CreateFixedCouponRequest createFixedCouponRequest);

	Long createRatioTypes(CreateRatioCouponRequest createRatioCouponRequest);
}