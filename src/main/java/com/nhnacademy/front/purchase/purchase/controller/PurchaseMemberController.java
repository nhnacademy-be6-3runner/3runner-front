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
import com.nhnacademy.front.purchase.purchase.service.PurchaseCouponService;
import com.nhnacademy.front.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private final PurchaseCouponService purchaseCouponService;
    private final PaymentControllerClient paymentControllerClient;

    @GetMapping("/purchases/members")
    public String purchase(Model model){

        List<ReadAllBookCartMemberResponse> items = bookCartGuestControllerClient
                .readAllBookCartMember().getBody().getData();

        List<AddressResponse> addresses = memberAddressControllerClient
                .readAllAddresses().getBody().getData();

        GetMemberResponse memberInfo = memberControllerClient
                .readById().getBody().getData();

        ApiResponse.Body<List<ReadCouponFormResponse>> response = couponControllerClient.readCoupons().getBody();

        List<CouponDiscountPriceDto> validCoupons = purchaseCouponService.getValidCoupons(items, response);

        model.addAttribute("coupons", validCoupons);
        model.addAttribute("response", items);
        model.addAttribute("addresses", addresses);
        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("orderNumber", UUID.randomUUID());

        return "purchase/member/purchase";
    }

    @PostMapping("/purchases/members/addresses")
    public String address(String addrDetail,
                          String roadAddrPart1,
                          String roadAddrPart2,
                          String zipNo,
                          String roadFullAddr,
                          Model model) {
        String state = roadAddrPart1.split(" ")[0];
        roadAddrPart1 = roadAddrPart1.replaceFirst(state, "");

        memberAddressControllerClient.createAddress(CreateAddressRequest.builder()
                .city(roadAddrPart1)
                .state(state)
                .road(zipNo)
                .name(addrDetail)
                .country("대한민국")
                .postalCode(zipNo)
                .build());

        model.addAttribute("roadFullAddr", roadFullAddr);

        return "purchase/member/address";
    }

    @PostMapping("/purchases/members/confirm")
    @ResponseBody
    public void purchase(
            @RequestParam(required = false) String discountedPrice,
            @RequestParam(required = false) String discountedPoint,
            @RequestParam(required = false) String isPacking,
            @RequestParam(required = false) String shipping,
            @RequestParam(required = false) String road,
            @RequestParam(required = false) Long couponFormId,
            @RequestBody String jsonBody) throws Exception {
        paymentControllerClient.confirmMemberPayment(discountedPrice, discountedPoint,isPacking, shipping, road, couponFormId, jsonBody);


        return;
    }
}
