package com.nhnacademy.front.purchase.purchase.controller;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseResponse;
import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailGuestService;

import lombok.RequiredArgsConstructor;

/**
 * 비회원 주문 내역확인 컨트롤러
 *
 * @author 정주혁
 *
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders/guests")
public class PurchaseDetailGuestController {

	private final PurchaseDetailGuestService purchaseGuestService;

	/**
	 *
	 * 비회원 주문내역 보여주기
	 *
	 * @param orderNumber 주문 번호
	 * @param page 주문내역 현재 페이지(책들만)
	 * @param size 주문내역 보여주는 요소 사이즈(책)
	 * @param sort 정렬
	 * @param model
	 * @return 주문 창으로 이동
	 */
	@GetMapping("/{orderNumber}")
	public String orders(
		@PathVariable(name = "orderNumber") String orderNumber,
		@RequestParam(name = "password",required = false) String password,
		Model model) {
		ReadPurchaseResponse response = purchaseGuestService.readGuestPurchases(orderNumber,password);
		if(Objects.isNull(response)){
			return "redirect:/orders/guests/login";
		}
		model.addAttribute("guestorder",response);
		model.addAttribute("guestorderbooks",purchaseGuestService.readGuestPurchaseBooks(orderNumber));
		return "/purchase/guest/order-detail-guest";
	}

	/**
	 * 비회원 주문 확인 창
	 * @return 비회원 주문 확인 창으로 이동
	 */
	@GetMapping("/login")
	public String login() {

		return "order-login";
	}

	/**
	 *
	 * 비회원 주문 확인 인증
	 *
	 * @param orderNumber 주문 번호
	 * @param password 비밀 번호
	 * @return
	 */
	@PostMapping
	public String login(
		@RequestParam String orderNumber,
		@RequestParam String password
		) {
		if(purchaseGuestService.validatePurchase(orderNumber, password)){

			return "redirect:/orders/guests/"+orderNumber+"?password="+password;
		}
		else{
			return "redirect:/orders/guests/login";
		}



	}

	/**
	 * 주문 확정
	 *
	 * @param purchaseId 주문 확정할 id
	 * @return 주문 확정후 다시 자기 페이지로 이동
	 */
	@PostMapping("/{purchaseId}")
	public String orderConfirmed(@PathVariable(name = "purchaseId") String purchaseId){
		purchaseGuestService.updatePurchaseStatus(purchaseId);

		return "redirect:/orders/guests/login";
	}


}
