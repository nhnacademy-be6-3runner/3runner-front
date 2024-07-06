package com.nhnacademy.front.purchase.admin.service;

import com.nhnacademy.front.book.book.dto.response.BookListResponse;
import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.book.categroy.dto.response.CategoryResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateBookCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateCategoryCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateFixedCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.request.CreateRatioCouponRequest;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponTypeResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponUsageResponse;
import com.nhnacademy.front.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AdminCouponPolicyService {
    List<CategoryParentWithChildrenResponse> getCategories();
    Page<BookListResponse> getBookes(int size, int page);

    List<ReadCouponUsageResponse> getUsages();
    List<ReadCouponTypeResponse> getTypes();

    Long createCategoryUsages(CreateCategoryCouponRequest createCategoryCouponRequest);

    Long createBookUsages(CreateBookCouponRequest createBookCouponRequest);

    Long createFixedTypes(CreateFixedCouponRequest createFixedCouponRequest);

    Long createRatioTypes(CreateRatioCouponRequest createRatioCouponRequest);
}