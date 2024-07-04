package com.nhnacademy.front.purchase.purchase.controller;

import com.nhnacademy.front.book.categroy.dto.response.CategoryChildrenResponse;
import com.nhnacademy.front.book.categroy.dto.response.CategoryParentWithChildrenResponse;
import com.nhnacademy.front.purchase.cart.dto.response.ReadAllBookCartMemberResponse;
import com.nhnacademy.front.purchase.cart.dto.response.ReadBookCartGuestResponse;
import com.nhnacademy.front.purchase.cart.feign.BookCartControllerClient;
import com.nhnacademy.front.purchase.purchase.dto.coupon.CouponDiscountPriceDto;
import com.nhnacademy.front.purchase.purchase.dto.coupon.response.ReadCouponFormResponse;
import com.nhnacademy.front.purchase.purchase.dto.member.request.CreateAddressRequest;
import com.nhnacademy.front.purchase.purchase.dto.member.response.AddressResponse;
import com.nhnacademy.front.purchase.purchase.dto.member.response.GetMemberResponse;
import com.nhnacademy.front.purchase.purchase.feign.*;
import com.nhnacademy.util.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PurchaseMemberController {
    private final BookCartControllerClient bookCartGuestControllerClient;
    private final CouponControllerClient couponControllerClient;
    private final MemberAddressControllerClient memberAddressControllerClient;
    private final MemberControllerClient memberControllerClient;
    private final BookCategoryControllerClient bookCategoryControllerClient;

    @GetMapping("/api/purchases/members")
    public String purchase(@RequestHeader(required = false) Long memberId, Model model){

        List<ReadAllBookCartMemberResponse> items = bookCartGuestControllerClient
                .readAllBookCartMember(memberId).getBody().getData();

        List<AddressResponse> addresses = memberAddressControllerClient
                .readAllAddresses(memberId).getBody().getData();

        GetMemberResponse memberInfo = memberControllerClient
                .readById(memberId).getBody().getData();

        ApiResponse.Body<List<ReadCouponFormResponse>> response = couponControllerClient.readCoupons(memberId).getBody();

        if (Objects.isNull(response)) {
            List<ReadCouponFormResponse> coupons = new ArrayList<>();
            model.addAttribute("coupons", coupons);
        }

        Set<Long> books = new HashSet<>();
        Set<Long> categorys = new HashSet<>();
        int totalPrice = 0;
        int discountPrice = 0;

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
                    ||readCouponFormResponse.categorys().stream().anyMatch(categorysLong::contains))
                    && readCouponFormResponse.endDate().isAfter(ZonedDateTime.now())) {
                if(readCouponFormResponse.discountPrice() != 0 && readCouponFormResponse.discountPrice() < readCouponFormResponse.discountMax()){
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
                    if (readCouponFormResponse.discountRate() != 0 && readCouponFormResponse.discountRate()*totalPrice > readCouponFormResponse.discountMax()) {
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
                    } else if (readCouponFormResponse.discountRate() != 0 && readCouponFormResponse.discountRate()*totalPrice < readCouponFormResponse.discountMax() ){
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
                                .finalDiscountPrice((int)(readCouponFormResponse.discountRate()*totalPrice))
                                .build()
                        );
                    }
                }
            }
        }

        model.addAttribute("coupons", validCoupons);
        model.addAttribute("response", items);
        model.addAttribute("memberId", memberId);
        model.addAttribute("addresses", addresses);
        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("orderNumber", UUID.randomUUID());

        return "/purchase/member/purchase";
    }

    @PostMapping("/api/purchases/members/addresses")
    public String address(@RequestHeader(value = "Member-Id", required = false) Long memberId,
                          String addrDetail,
                          String roadAddrPart1,
                          String roadAddrPart2,
                          String zipNo,
                          String roadFullAddr,
                          Model model) {

        memberAddressControllerClient.createAddress(CreateAddressRequest.builder()
                .city(roadAddrPart1)
                .state(roadAddrPart2)
                .road(zipNo)
                .name(addrDetail)
                .country("대한민국")
                .postalCode(zipNo).build()
                ,memberId);

        model.addAttribute("roadFullAddr", roadFullAddr);

        return "/purchase/member/address";
    }
}
