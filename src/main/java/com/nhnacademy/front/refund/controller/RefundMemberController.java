package com.nhnacademy.front.refund.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import com.nhnacademy.front.refund.feign.RefundRecordMemberControllerClient;
import com.nhnacademy.front.refund.service.RefundService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/refund/members")
public class RefundMemberController {

	private final RefundService refundService;
	private final RefundRecordMemberControllerClient refundRecordMemberControllerClient;

	//Todo : security에 걸리게 링크 수정
	@GetMapping("/{orderNumber}")
	public String refund(@PathVariable(name = "orderNumber") Long purchaseId,@RequestParam(name="type") String type
		, Model model) {


		model.addAttribute("type", type);
		List<ReadPurchaseBookResponse> books = refundService.readMemberPurchaseBooks(purchaseId);

		model.addAttribute("books", books);

		return "refund";
	}

	@ResponseBody
	@PostMapping("/cancelPayment/{orderNumber}")
	public ResponseEntity<Map<String, String>> cancelPayment(@PathVariable(name = "orderNumber") Long orderNumber, @RequestParam(name = "refund-price", required = false) Integer price, Model model) {

		Map<String, Object> result = refundService.refundToss(orderNumber, price, "결제 취소");

		Boolean isSuccess = (Boolean)result.get("isSuccess");
		JSONObject json = (JSONObject)result.get("jsonObject");

		model.addAttribute("isSuccess", isSuccess);
		model.addAttribute("jsonObject", json);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Refund request successful");
		response.put("redirectUrl", "/refund/members/success");

		return ResponseEntity.ok(response);
	}

	@ResponseBody
	@PostMapping("/{purchaseId}")
	public ResponseEntity<Map<String, String>> refundRequest(@PathVariable(name = "purchaseId") Long purchaseId,

		@RequestParam(name = "refund-content") String refundContent,
		@RequestParam(name = "refund-price") Integer price) {
		Long refundId = refundService.createRefundRequest(purchaseId, price, refundContent);
		if(Boolean.FALSE.equals(refundRecordMemberControllerClient.createRefundRecordMember(purchaseId,refundId).getBody().getData())){
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("message", "환불 요청이 완료된 건입니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}

		Map<String, String> response = new HashMap<>();
		response.put("message", "Refund request successful");
		response.put("redirectUrl", "/refund/members/success");

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{orderNumber}/update/{purchaseBookId}")
	public String updateRefund(@PathVariable(name = "orderNumber") Long orderNumber, @PathVariable(name = "purchaseBookId") Long purchaseBookId, @RequestParam(name = "quantity", required = false) int quantity, Model model) {
		refundRecordMemberControllerClient.updateRefundRecordMember(orderNumber,purchaseBookId, quantity);
		return "redirect:/refund/members/"+orderNumber;
	}

	@GetMapping("/{orderNumber}/update/all")
	public String updateRefundAll(@PathVariable(name = "orderNumber") Long orderNumber) {
		refundRecordMemberControllerClient.updateRefundRecordAllMember(orderNumber);
		return "redirect:/refund/members/"+orderNumber;
	}

	@GetMapping("/{orderNumber}/update/all/zero")
	public String updateRefundAllZero(@PathVariable(name = "orderNumber") Long orderNumber) {
		refundRecordMemberControllerClient.updateRefundRecordAllZeroMember(orderNumber);
		return "redirect:/refund/members/"+orderNumber;
	}

	@GetMapping("/success")
	public String success(Model model) {
		model.addAttribute("result", "success");
		return "refundSuccess";
	}
}
