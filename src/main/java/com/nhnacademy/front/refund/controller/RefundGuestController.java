package com.nhnacademy.front.refund.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.refund.service.RefundService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/refund/guests")
public class RefundGuestController {
	private final RefundService refundService;

	@GetMapping("/{orderNumber}")
	public String refund(@PathVariable String orderNumber, Model model) {
		List< ReadPurchaseBookResponse> books = refundService.readGuestPurchaseBooks(orderNumber);
		model.addAttribute("books", books);
		return "refundGuest";
	}





}
