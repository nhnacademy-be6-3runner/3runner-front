package com.nhnacademy.front.refund.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
		model.addAttribute("orderNumber", orderNumber);
		model.addAttribute("books", books);
		return "refundGuest";
	}

	@GetMapping("/{orderNumber}/update/{purchaseBookId}")
	public String updateRefund(@PathVariable(name = "orderNumber") String orderNumber, @PathVariable(name = "purchaseBookId") Long purchaseBookId, @RequestParam(name = "quantity", required = false) int quantity, Model model) {
		refundRecordGuestControllerClient.updateRefundRecordGuest(orderNumber,purchaseBookId, quantity);
		return "";
	}

	@GetMapping("/{orderNumber}/update/all")
	public String updateRefundAll(@PathVariable(name = "orderNumber") String orderNumber, Model model) {
		refundRecordGuestControllerClient.updateRefundRecordAllGuest(orderNumber);
		return "";

	}

	@GetMapping("/{orderNumber}/update/all/zero")
	public String updateRefundAllZero(@PathVariable(name = "orderNumber") String orderNumber) {
		refundRecordGuestControllerClient.updateRefundRecordAllZeroMember(orderNumber);
		return "";

	}

	@ResponseBody
	@PostMapping("/cancelPayment/{orderNumber}")
	public ResponseEntity<Map<String, String>> cancelPayment(@PathVariable(name = "orderNumber") String orderNumber
		, @RequestParam(name = "refund-price", required = false) Integer price
		, Model model) {


		Map<String, Object> result = refundService.refundToss(orderNumber, price, "결제 취소");


		JSONObject json = (JSONObject)result.get("jsonObject");

		Map<String, String> response = new HashMap<>();
		response.put("message", "Refund request successful");
		response.put("redirectUrl", "/");

		return ResponseEntity.ok(response);
	}






}
