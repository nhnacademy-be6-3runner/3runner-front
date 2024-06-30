package com.nhnacademy.front.purchase.purchase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.purchase.purchase.service.PurchaseDetailGuestService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guest/orders")
public class PurchaseDetailGuestController {

	private final PurchaseDetailGuestService purchaseGuestService;


	@GetMapping
	public String orders(Model model) {


		return null;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping
	public String login(
		@RequestParam String username,
		@RequestParam String password,
		Model model) {
		if(purchaseGuestService.validatePurchase(username, password)){
			return "redirect:/guest/orders";
		}
		else{
			model.addAttribute("message", "주문번호 또는 비밀번호가 틀렸습니다.");
		}


		return "login";
	}


}
