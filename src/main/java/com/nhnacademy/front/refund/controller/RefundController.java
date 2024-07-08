package com.nhnacademy.front.refund.controller;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.refund.service.RefundService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/refund")
public class RefundController {

	private final RefundService refundService;

	@GetMapping("/{orderNumber}")
	public String refund(@PathVariable(name = "orderNumber") String purchaseId
		, Model model) {

		List<ReadPurchaseBookResponse> books = refundService.readGuestPurchaseBooks(purchaseId);
		model.addAttribute("books", books);

		return "refund";
	}

	@PostMapping("/cancelPayment/{orderNumber}")
	public String cancelPayment(@PathVariable(name = "orderNumber") String orderNumber, Model model) {

		Map<String, Object> result = refundService.refundToss(orderNumber,"결제 취소");

		Boolean isSuccess = (Boolean)result.get("isSuccess");
		JSONObject json = (JSONObject)result.get("jsonObject");

		model.addAttribute("isSuccess", isSuccess);
		model.addAttribute("jsonObject", json);

		return "result";
	}

}
