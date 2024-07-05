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
    private final PurchaseCouponService purchaseCouponService;

    @GetMapping("/purchases/members")
    public String purchase(@RequestHeader(required = false) Long memberId, Model model){

        List<ReadAllBookCartMemberResponse> items = bookCartGuestControllerClient
                .readAllBookCartMember(memberId).getBody().getData();

        List<AddressResponse> addresses = memberAddressControllerClient
                .readAllAddresses(memberId).getBody().getData();

        GetMemberResponse memberInfo = memberControllerClient
                .readById(memberId).getBody().getData();

        ApiResponse.Body<List<ReadCouponFormResponse>> response = couponControllerClient.readCoupons(memberId).getBody();

        List<CouponDiscountPriceDto> validCoupons = purchaseCouponService.getValidCoupons(items, response);

        model.addAttribute("coupons", validCoupons);
        model.addAttribute("response", items);
        model.addAttribute("memberId", memberId);
        model.addAttribute("addresses", addresses);
        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("orderNumber", UUID.randomUUID());

        return "/purchase/member/purchase";
    }

    @PostMapping("/purchases/members/addresses")
    public String address(@RequestHeader(value = "Member-Id", required = false) Long memberId,
                          String addrDetail,
                          String roadAddrPart1,
                          String roadAddrPart2,
                          String zipNo,
                          String roadFullAddr,
                          Model model) {

        memberAddressControllerClient.createAddress(CreateAddressRequest.builder().city(roadAddrPart1).state(roadAddrPart2).road(zipNo).name(addrDetail).country("대한민국").postalCode(zipNo).build(),memberId);

        model.addAttribute("roadFullAddr", roadFullAddr);

        return "/purchase/member/address";
    }
}
