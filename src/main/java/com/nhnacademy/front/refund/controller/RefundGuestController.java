package com.nhnacademy.front.refund.controller;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhnacademy.front.purchase.purchase.dto.purchase.response.ReadPurchaseBookResponse;
import com.nhnacademy.front.refund.feign.RefundRecordGuestControllerClient;
import com.nhnacademy.front.refund.service.RefundService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/refund/guests")
public class RefundGuestController {
	private final RefundService refundService;
	private final RefundRecordGuestControllerClient refundRecordGuestControllerClient;

	@GetMapping("/{orderNumber}")
	public String refund(@PathVariable String orderNumber, Model model) {
		List< ReadPurchaseBookResponse> books = refundService.readGuestPurchaseBooks(orderNumber);
		model.addAttribute("books", books);
		return "refundGuest";
	}

	@GetMapping("/{orderNumber}/update/{purchaseBookId}")
	public String updateRefund(@PathVariable(name = "orderNumber") String orderNumber, @PathVariable(name = "purchaseBookId") Long purchaseBookId, @RequestParam(name = "quantity", required = false) int quantity, Model model) {
		refundRecordGuestControllerClient.updateRefundRecordGuest(orderNumber,purchaseBookId, quantity);
		return "redirect:/refund/guests/"+orderNumber;
	}

	@GetMapping("/{orderNumber}/update/all")
	public String updateRefundAll(@PathVariable(name = "orderNumber") String orderNumber, Model model) {
		refundRecordGuestControllerClient.updateRefundRecordAllGuest(orderNumber);
		return "redirect:/refund/guests"+orderNumber;
	}

	@GetMapping("/{orderNumber}/update/all/zero")
	public String updateRefundAllZero(@PathVariable(name = "orderNumber") String orderNumber) {
		refundRecordGuestControllerClient.updateRefundRecordAllZeroMember(orderNumber);
		return "redirect:/refund/guests"+orderNumber;
	}

	@PostMapping("/cancelPayment/{orderNumber}")
	public String cancelPayment(@PathVariable(name = "orderNumber") String orderNumber, @RequestParam(name = "price", required = false) Integer price, Model model) {

		Map<String, Object> result = refundService.refundToss(orderNumber, price, "결제 취소");

		Boolean isSuccess = (Boolean)result.get("isSuccess");
		JSONObject json = (JSONObject)result.get("jsonObject");

		model.addAttribute("isSuccess", isSuccess);
		model.addAttribute("jsonObject", json);

		return "result";
	}






}
