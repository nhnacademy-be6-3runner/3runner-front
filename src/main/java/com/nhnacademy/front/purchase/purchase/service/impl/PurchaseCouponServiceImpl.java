package com.nhnacademy.front.purchase.purchase.service.impl;

import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.purchase.dto.coupon.CouponDiscountPriceDto;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponFormResponse;
import com.nhnacademy.front.purchase.purchase.feign.BookCategoryControllerClient;
import com.nhnacademy.front.purchase.purchase.service.PurchaseCouponService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseCouponServiceImpl implements PurchaseCouponService {
    private final BookCategoryControllerClient bookCategoryControllerClient;
    @Override
    public List<CouponDiscountPriceDto> getValidCoupons(List<ReadAllBookCartMemberResponse> items,
                                                        ApiResponse.Body<List<ReadCouponFormResponse>> response) {
        if (response == null) {
            return new ArrayList<>();
        }

        Set<Long> books = new HashSet<>();
        Set<Long> categorys = new HashSet<>();
        int totalPrice = 0;

        for(ReadAllBookCartMemberResponse item : items){
            books.add(item.bookId());
            List<CategoryParentWithChildrenResponse> categoryChildrenResponses = bookCategoryControllerClient.readCategories(item.bookId()).getBody().getData();

            for(CategoryParentWithChildrenResponse categoryParentWithChildrenResponse : categoryChildrenResponses){
                categorys.add(categoryParentWithChildrenResponse.getId());
                for(CategoryParentWithChildrenResponse childrenResponse : categoryParentWithChildrenResponse.getChildrenList()){
                    categorys.add(childrenResponse.getId());
                }
            }
            totalPrice += item.price() * item.quantity();
        }

        List<Long> booksLong = books.stream().toList();
        List<Long> categorysLong = categorys.stream().toList();

        List<ReadCouponFormResponse> coupons = response.getData();
        List<CouponDiscountPriceDto> validCoupons = new ArrayList<>();
        for(ReadCouponFormResponse readCouponFormResponse : coupons){
            if ((readCouponFormResponse.books().stream().anyMatch(booksLong::contains)
                    || readCouponFormResponse.categorys().stream().anyMatch(categorysLong::contains))
                    && readCouponFormResponse.endDate().isAfter(ZonedDateTime.now())) {
                if(readCouponFormResponse.discountPrice() != 0){
                    validCoupons.add(CouponDiscountPriceDto.builder()
                            .couponFormId(readCouponFormResponse.couponFormId())
                            .startDate(readCouponFormResponse.startDate())
                            .endDate(readCouponFormResponse.endDate())
                            .name(readCouponFormResponse.name())
                            .code(readCouponFormResponse.code())
                            .maxPrice(readCouponFormResponse.maxPrice())
                            .minPrice(readCouponFormResponse.minPrice())
                            .couponTypeId(readCouponFormResponse.couponTypeId())
                            .couponUsageId(readCouponFormResponse.couponUsageId())
                            .type(readCouponFormResponse.type())
                            .usage(readCouponFormResponse.usage())
                            .books(readCouponFormResponse.books())
                            .categorys(readCouponFormResponse.categorys())
                            .discountPrice(readCouponFormResponse.discountPrice())
                            .discountRate(readCouponFormResponse.discountRate())
                            .discountMax(readCouponFormResponse.discountMax())
                            .finalDiscountPrice(readCouponFormResponse.discountPrice())
                            .build()
                    );

                } else {
                    if (readCouponFormResponse.discountRate() != 0 && readCouponFormResponse.discountRate() * totalPrice > readCouponFormResponse.discountMax()) {
                        validCoupons.add(CouponDiscountPriceDto.builder()
                                .couponFormId(readCouponFormResponse.couponFormId())
                                .startDate(readCouponFormResponse.startDate())
                                .endDate(readCouponFormResponse.endDate())
                                .name(readCouponFormResponse.name())
                                .code(readCouponFormResponse.code())
                                .maxPrice(readCouponFormResponse.maxPrice())
                                .minPrice(readCouponFormResponse.minPrice())
                                .couponTypeId(readCouponFormResponse.couponTypeId())
                                .couponUsageId(readCouponFormResponse.couponUsageId())
                                .type(readCouponFormResponse.type())
                                .usage(readCouponFormResponse.usage())
                                .books(readCouponFormResponse.books())
                                .categorys(readCouponFormResponse.categorys())
                                .discountPrice(readCouponFormResponse.discountPrice())
                                .discountRate(readCouponFormResponse.discountRate())
                                .discountMax(readCouponFormResponse.discountMax())
                                .finalDiscountPrice(readCouponFormResponse.discountMax())
                                .build()
                        );
                    } else if (readCouponFormResponse.discountRate() != 0 && readCouponFormResponse.discountRate() * totalPrice < readCouponFormResponse.discountMax() ){
                        validCoupons.add(CouponDiscountPriceDto.builder()
                                .couponFormId(readCouponFormResponse.couponFormId())
                                .startDate(readCouponFormResponse.startDate())
                                .endDate(readCouponFormResponse.endDate())
                                .name(readCouponFormResponse.name())
                                .code(readCouponFormResponse.code())
                                .maxPrice(readCouponFormResponse.maxPrice())
                                .minPrice(readCouponFormResponse.minPrice())
                                .couponTypeId(readCouponFormResponse.couponTypeId())
                                .couponUsageId(readCouponFormResponse.couponUsageId())
                                .type(readCouponFormResponse.type())
                                .usage(readCouponFormResponse.usage())
                                .books(readCouponFormResponse.books())
                                .categorys(readCouponFormResponse.categorys())
                                .discountPrice(readCouponFormResponse.discountPrice())
                                .discountRate(readCouponFormResponse.discountRate())
                                .discountMax(readCouponFormResponse.discountMax())
                                .finalDiscountPrice((int)(readCouponFormResponse.discountRate() * totalPrice))
                                .build()
                        );
                    }
                }
            }
        }

        return validCoupons;
    }
}
