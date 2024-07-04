package com.nhnacademy.front.purchase.purchase.controller;

import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailGuestService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders/guests")
public class PurchaseDetailGuestController {

	private final PurchaseDetailGuestService purchaseGuestService;


	@GetMapping("/{orderNumber}")
	public String orders(
		@PathVariable(name = "orderNumber") String orderNumber,
		@ModelAttribute("password") String password,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "30") int size,
		@RequestParam(required = false) String sort,
		Model model) {

		model.addAttribute("guestorder",purchaseGuestService.readGuestPurchases(orderNumber,password));
		model.addAttribute("guestorderbooks",purchaseGuestService.readGuestPurchaseBooks(orderNumber,page,size,sort));
		return "/purchase/guest/order-detail-guest";
	}

	@GetMapping("/login")
	public String login() {

		return "order-login";
	}

	@PostMapping
	public String login(
		@RequestParam String orderNumber,
		@RequestParam String password,
		RedirectAttributes redirectAttributes,
		Model model) {
		if(purchaseGuestService.validatePurchase(orderNumber, password)){
			redirectAttributes.addAttribute("password", password);
			return "redirect:/guest/orders/"+orderNumber;
		}
		else{
			model.addAttribute("message", "주문번호 또는 비밀번호가 틀렸습니다.");
			return "redirect:/guest/orders/login";
		}



	}

	@PostMapping("/{purchaseId}")
	public String orderConfirmed(@PathVariable(name = "purchaseId") String purchaseId){
		purchaseGuestService.updatePurchaseStatus(purchaseId);


		return "redirect:/order/guest";
	}


}
