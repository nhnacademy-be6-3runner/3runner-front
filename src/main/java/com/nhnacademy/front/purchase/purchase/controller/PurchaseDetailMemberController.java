package com.nhnacademy.front.purchase.purchase.controller;

import java.util.HashMap;
import java.util.Map;

import com.nhnacademy.front.entity.purchase.enums.PurchaseStatus;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchase;
import com.nhnacademy.front.purchase.purchase.dto.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailMemberService;
import com.nhnacademy.util.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 회원 주문내역 조회 controller
 *
 * @author 정주혁
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class PurchaseDetailMemberController {
    private final PurchaseDetailMemberService purchaseMemberService;

    /**
     * 주문 내역 조회 페이지 이동(guest,member 동일)
     * @return 주문내역 조회
     */
    @GetMapping
    public String orderDetail(){
        return "purchase/order-detail";
    }

    /**
     * 회원 주문 내역 조회
     * @param model
     * @return 회원 주문내역 조회 페이지 이동(주문 내역조회페이지와 동일, 숨겨진 주문내역 보여짐)
     */
    @GetMapping("/members")
    public String orderDetailMember(Model model){


        Page<ReadPurchase> orderDetail = purchaseMemberService.readPurchases();

        model.addAttribute("orders", orderDetail);

        return "purchase/order-detail";
    }

    /**
     * 주문 상세 내역(구매한 책들) 조회
     *
     * @param purchaseId 주문 내역 id(실제 id)
     * @param page 주문 책 현재 페이지
     * @param size 한번에 보여줄 주문책들 개수
     * @param search 정렬
     * @param model
     * @return 주문 상세 내역 페이지로 이동
     */
    @GetMapping("/members/books/{purchaseId}")
    public String orderDetailBooks(
        @PathVariable(value = "purchaseId") Long purchaseId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "30") int size,
        @RequestParam(required = false) String search,
        Model model) {
        Page<ReadPurchaseBookResponse> orderDetailBooks = purchaseMemberService.readPurchaseBookResponses(purchaseId, page,
            size, search);
        PurchaseStatus purchaseStatus = purchaseMemberService.readPurchaseStatus(purchaseId);
        model.addAttribute("books", orderDetailBooks);
        model.addAttribute("purchaseStatus", purchaseStatus.toString());

        return "purchase/order-detail-book";
    }

    /**
     * 주문 확정
     *
     * @param purchaseId 주문 확정할 주문 id
     * @return 주문 내역 페이지로 이동
     */
    @PostMapping("/members/{purchaseId}")
    public String orderConfirmed(@PathVariable(name = "purchaseId") long purchaseId){
        purchaseMemberService.updatePurchaseStatus(purchaseId);

        return "redirect:/orders/members";
    }

    /**
     * 헤더가 있는지 없는지 판단하는 코드(member 확인, javaScript에서 사용되는 코드)
     *
     * @param memberId 헤더에있는 member
     * @return key: isLoggedIn value : memberId헤더 존재 유무
     */
    @GetMapping("/check")
    public ApiResponse<Map<String, Boolean>> checkMemberId(@RequestHeader(value = "member-id", required = false) Long memberId) {
        Map<String, Boolean> response = new HashMap<>();
        if (memberId != null) {
            response.put("isLoggedIn", true);
        } else {
            response.put("isLoggedIn", false);
        }
        return ApiResponse.success(response);
    }


}